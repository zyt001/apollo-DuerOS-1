package com.baidu.carlife.sdk.carlifetest

import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.baidu.carlife.sdk.carlifetest.Helper.iMEI
import com.baidu.carlife.sdk.util.Logger
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.LinkedBlockingQueue

class ProtocolUploadQueue {
    companion object {
        private const val MIN_UPLOAD_SIZE = 10
        private const val DEBOUNCE_INTERVAL = 5000
        private var DEVICE_NAME: String? = null
        private var SERIAL_NUMBER: String? = null
        val deviceName: String
            get() {
                val manufacturer = Build.MANUFACTURER
                val model = Build.MODEL
                return if (model.startsWith(manufacturer)) {
                    model
                } else "$manufacturer $model"
            }

        init {
            DEVICE_NAME = deviceName
            SERIAL_NUMBER = iMEI
        }
    }

    private val pendingMessages = LinkedBlockingQueue<ProtocolMessage>()
    private val uploadMessages = LinkedBlockingQueue<ProtocolMessage>()
    private val handler: Handler
    private val client = OkHttpClient()
    private val gson = GsonBuilder().create()
    fun enqueue(channel: String?, message: ProtocolMessage) {
        fillOtherInfo(message, channel)
        if (channel == null) {
            pendingMessages.add(message)
            return
        }
        while (!pendingMessages.isEmpty()) {
            val item = pendingMessages.poll()
            if (item != null) {
                item.channel = channel
                uploadMessages.offer(item)
            }
        }
        uploadMessages.offer(message)
        handler.removeCallbacksAndMessages(null)
        if (uploadMessages.size >= MIN_UPLOAD_SIZE) {
            handler.post { doUpload() }
        } else {
            // 如果数量未达到10个，则延迟5秒执行，尽量合并上传
            handler.postDelayed({ doUpload() }, DEBOUNCE_INTERVAL.toLong())
        }
    }

    private fun fillOtherInfo(message: ProtocolMessage, channel: String?) {
        message.channel = channel
        message.name = DEVICE_NAME
        message.serialNumber = SERIAL_NUMBER
    }

    private fun doUpload() {
        if (!uploadMessages.isEmpty()) {
            val items = ArrayList<ProtocolMessage>()
            uploadMessages.drainTo(items)
            val json = gson.toJson(items)
            Logger.d("ProtocolAnalyzer", "doUpload json: $json")
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            val request = Request.Builder()
                .url("http://182.61.17.40:5000/messages")
                .post(body)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logger.d("ProtocolAnalyzer", "doUpload failed: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    Logger.d("ProtocolAnalyzer", "doUpload success: $response")
                    if (response.body() != null) {
                        response.close()
                    }
                }
            })
        }
    }

    init {
        val thread = HandlerThread("ProtocolUploadQueue")
        thread.start()
        handler = Handler(thread.looper)
    }
}
package com.baidu.carlife.sdk.carlifetest

import com.baidu.carlife.protobuf.CarlifeStatisticsInfoProto.CarlifeStatisticsInfo
import com.baidu.carlife.sdk.BuildConfig
import com.baidu.carlife.sdk.Constants
import com.baidu.carlife.sdk.internal.protocol.CarLifeMessage
import com.baidu.carlife.sdk.internal.protocol.ServiceTypes

class ProtocolAnalyzer {
    private val uploadQueue = ProtocolUploadQueue()

    private var channel: String? = null

    fun onReceiveMessage(message: CarLifeMessage?) {
        if (message == null) {
            return
        }

        if (message.serviceType == ServiceTypes.MSG_VIDEO_HEARTBEAT
            || message.serviceType == ServiceTypes.MSG_VIDEO_DATA
            || message.serviceType == ServiceTypes.MSG_CMD_FOREGROUND
            || message.serviceType == ServiceTypes.MSG_VR_DATA) {
            return
        }

        if (BuildConfig.DEBUG || Constants.ISUPLOAD) {
            channel = findChannel(message)

            val serviceType = ServiceTypes.getMsgName(message.serviceType)

            uploadQueue.enqueue(channel, ProtocolMessage.create("RECEIVE", "CMD", serviceType,
                message.protoPayload?.toString() ?: ""))
        }
    }

    fun onSendMessage(message: CarLifeMessage?) {
        if (message == null) {
            return
        }

        if (message.serviceType == ServiceTypes.MSG_VIDEO_HEARTBEAT
            || message.serviceType == ServiceTypes.MSG_VIDEO_DATA
            || message.serviceType == ServiceTypes.MSG_CMD_FOREGROUND
            || message.serviceType == ServiceTypes.MSG_VR_DATA) {
            return
        }

        if (BuildConfig.DEBUG || Constants.ISUPLOAD) {
            if (message.serviceType == ServiceTypes.MSG_CMD_MEDIA_PROGRESS_BAR) {
                // 这个太频繁，不记录了
                return
            }

            channel = findChannel(message)

            val serviceType = ServiceTypes.getMsgName(message.serviceType)

            uploadQueue.enqueue(channel, ProtocolMessage.create("SEND", "CMD", serviceType,
                message.protoPayload?.toString() ?: ""))
        }
    }

    private fun findChannel(message: CarLifeMessage): String? {
            return when(message.serviceType) {
                ServiceTypes.MSG_CMD_STATISTIC_INFO -> {
                    val payload = message.protoPayload as CarlifeStatisticsInfo
                    payload.channel
                }
                ServiceTypes.MSG_CMD_HU_PROTOCOL_VERSION -> null
                else -> channel

            }
    }

    companion object {
        @Volatile
        var instance: ProtocolAnalyzer? = null
            get() {
                if (field == null) {
                    synchronized(ProtocolAnalyzer::class.java) {
                        if (field == null) {
                            field = ProtocolAnalyzer()
                        }
                    }
                }
                return field
            }
            private set
    }
}
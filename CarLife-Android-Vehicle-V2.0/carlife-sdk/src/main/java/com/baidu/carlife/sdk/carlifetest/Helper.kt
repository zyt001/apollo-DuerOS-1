package com.baidu.carlife.sdk.carlifetest

import android.os.Environment
import com.baidu.carlife.sdk.util.Logger.Companion.d
import java.io.*
import java.lang.Exception

object Helper {
    // 创建随机Key值
    @JvmStatic
    val iMEI: String
        get() {
            val path = Environment.getExternalStorageDirectory().absolutePath + File.separator + "imei.txt"
            val keyFile = File(path)
            var randomStr = System.currentTimeMillis().toString() + "" // 创建随机Key值
            if (!keyFile.exists()) {
                try {
                    keyFile.createNewFile()
                    val fw = FileWriter(keyFile)
                    while (randomStr.length < 15) {
                        randomStr += System.currentTimeMillis()
                    }
                    if (randomStr.length > 15) {
                        randomStr = randomStr.substring(randomStr.length - 15)
                    }
                    fw.write(randomStr)
                    fw.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    val fr = BufferedReader(FileReader(keyFile))
                    randomStr = fr.readLine().trim { it <= ' ' }
                    while (randomStr.length < 15) {
                        randomStr += System.currentTimeMillis()
                    }
                    if (randomStr.length > 15) {
                        randomStr = randomStr.substring(randomStr.length - 15)
                        keyFile.delete()
                        keyFile.createNewFile()
                        val fw = FileWriter(keyFile)
                        fw.write(randomStr)
                        fw.close()
                    }
                    fr.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            d("RandomImei", randomStr)
            return randomStr
        }
}
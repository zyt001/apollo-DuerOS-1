package com.baidu.carlifevehicle

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.IBinder
import android.util.Log
import com.baidu.carlife.sdk.CarLifeContext
import com.baidu.carlife.sdk.Configs.*
import com.baidu.carlife.sdk.Constants
import com.baidu.carlife.sdk.Constants.TAG
import com.baidu.carlife.sdk.receiver.CarLife
import com.baidu.carlife.sdk.sender.display.DisplaySpec
import com.baidu.carlife.sdk.internal.audio.recorder.VoiceManager
import com.baidu.carlifevehicle.protocol.ControllerHandler
import com.baidu.carlifevehicle.util.CarlifeConfUtil

class VehicleApplication : Application() {

    var vehicleBind: VehicleService.VehicleBind? = null

    companion object {
        lateinit var app: Application

        const val VID_ACCESSORY = 0x18D1
        const val PID_ACCESSORY_ONLY = 0x2D00
        const val PID_ACCESSORY_AUDIO_ADB_BULK = 0x2D05

    }


    override fun onCreate() {
        super.onCreate()
        app = this

        resetUsbDeviceIfNecessary()
        CarlifeConfUtil.getInstance().init()
        initReceiver()
        bindVehicleService()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initReceiver() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val displaySpec = DisplaySpec(
            this,
            screenWidth.coerceAtLeast(screenHeight).coerceAtMost(1920),
            screenWidth.coerceAtMost(screenHeight).coerceAtMost(1080),
            30
        )

        val features = mapOf(
            FEATURE_CONFIG_USB_MTU to 16 * 1024,
            FEATURE_CONFIG_I_FRAME_INTERVAL to 300,
            FEATURE_CONFIG_CONNECT_TYPE to CarLifeContext.CONNECTION_TYPE_AOA
        )

        val configs = mapOf(
            CONFIG_LOG_LEVEL to Log.DEBUG,
            CONFIG_CONTENT_ENCRYPTION to true,
            CONFIG_USE_ASYNC_USB_MODE to false,
            CONFIG_PROTOCOL_VERSION to 4,
            CONFIG_TARGET_BLUETOOTH_NAME to "MI88",
            CONFIG_WIFI_DIRECT_NAME to "Mi9Pro5G",
            CONFIG_VOICE_RECORD_SDK to true
        )

        Log.d(
            Constants.TAG,
            "VehicleApplication initReceiver $screenWidth, $screenHeight $displaySpec"
        )
        CarLife.init(
            this,
            "20029999",
            "12345678",
            features,
            CarlifeActivity::class.java,
            configs
        )
        VoiceManager.init(CarLife.receiver())
        CarLife.receiver().setDisplaySpec(displaySpec)
        CarLife.receiver().registerTransportListener(ControllerHandler())


//        CarLife.receiver().addSubscriber(AssistantGuideSubscriber(CarLife.receiver()))
//        CarLife.receiver().addSubscriber(TurnByTurnSubscriber(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataGPSSubscribable(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataVelocitySubscribable(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataGyroscopeSubscribable(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataAccelerationSubscribable(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataGearSubscribable(CarLife.receiver()))
//        CarLife.receiver().addSubscribable(CarDataOilSubscribable(CarLife.receiver()))
    }

    val vehicleConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            vehicleBind = service as VehicleService.VehicleBind
        }
    }

    fun bindVehicleService() {
        val intent = Intent(this, VehicleService::class.java)
        bindService(intent, vehicleConnection, Context.BIND_AUTO_CREATE)
    }

    private fun resetUsbDeviceIfNecessary() {

        val usbManager = applicationContext.getSystemService(Context.USB_SERVICE) as UsbManager
        for (device in usbManager.deviceList.values) {
            if (isAccessory(device)) {
                try {
                    val usbConnection = usbManager.openDevice(device)
                    usbConnection.releaseInterface(device.getInterface(0))
                    val resetDevice = UsbDeviceConnection::class.java.getMethod("resetDevice")
                    val result = resetDevice.invoke(usbConnection) //调用借钱方法，得到返回值
                    Log.e(TAG, "USB Device resetDevice result: $result")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isAccessory(usbDevice: UsbDevice): Boolean {
        return usbDevice.vendorId == VID_ACCESSORY
                && usbDevice.productId >= PID_ACCESSORY_ONLY
                && usbDevice.productId <= PID_ACCESSORY_AUDIO_ADB_BULK
    }
}
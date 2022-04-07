package com.baidu.carlife.sdk.carlifetest

class ProtocolMessage {
    var channel: String? = null

    var name: String? = null

    var serialNumber: String? = null

    var direction: String? = null

    var entry: String? = null

    var protocol: String? = null

    var params: String? = null

    var timestamp: Long = 0

    companion object {
        fun create(direction: String?, entry: String?, protocol: String?, params: String?): ProtocolMessage {
            val message = ProtocolMessage()
            message.direction = direction
            message.entry = entry
            message.protocol = protocol
            message.params = params
            message.timestamp = System.currentTimeMillis()
            return message
        }
    }
}
package core

import akka.actor.AbstractActor
import akka.japi.pf.ReceiveBuilder
import org.apache.log4j.Logger
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

val log: Logger = Logger.getRootLogger()

class MqttActor: AbstractActor() {

    private val clientId = "haizhi-1"
    private val pullTopic = "Noah/pull/$clientId/#"
    private val pushTopic = "Noah/push/$clientId"
    private val qos = 2
    private val broker = "tcp://mqtt.ionull.com:1883"
    private val persistence = MemoryPersistence()
    private val client = MqttClient(broker, clientId, persistence)
    private val options = MqttConnectOptions()

    private class Callback: MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            log.info("Message Arrival $topic -> ${message.toString()}")
            when (topic) {
                "test" -> println(message.toString())
            }
        }

        override fun connectionLost(cause: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {

        }
    }

    init {
        options.isCleanSession = true
        client.setCallback(Callback())
        client.connect(options)
        client.subscribe(pullTopic, 2)
    }

    data class Message(val type: Type = Type.ALIVE, val payload: Map<Any, Any>? = null) {
        enum class Type {
            ALIVE
        }
    }

    override fun preStart() {
        super.preStart()
        log.info("Active on ${self.path()}")
    }

    override fun postStop() {
        super.postStop()
        log.info("Actor ${self.path()} stopped")
    }

    override fun createReceive(): Receive {
        return ReceiveBuilder()
                .match(Message::class.java) { client.publish(pushTopic, it.toString().toByteArray(), qos, false) }
                .build()
    }
}
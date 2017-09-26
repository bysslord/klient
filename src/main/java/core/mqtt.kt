package core

import akka.japi.pf.ReceiveBuilder
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


class MqttActor: VerboseActor() {

    private val clientId = "haizhi-1"
    private val pullTopic = "Noah/pull/$clientId"
    private val pushTopic = "Noah/push/$clientId"
    private val qos = 2
    private val broker = "tcp://mqtt.ionull.com:1883"
    private val persistence = MemoryPersistence()
    private val client = MqttClient(broker, clientId, persistence)
    private val options = MqttConnectOptions()

    fun String.akkaPath(): String {
        return this@akkaPath.replace(pullTopic, "")
    }

    init {
        options.isCleanSession = true
        client.setCallback(object: MqttCallback{
            override fun messageArrived(topic: String, message: MqttMessage?) {
                val akkaPath = topic.akkaPath()
                log.info("Message Arrival $topic: ${message.toString()} -> $akkaPath")
                context.actorSelection(akkaPath).tell(message, self)
            }

            override fun connectionLost(cause: Throwable?) {
                log.error("Connection Lost by ${cause.toString()}")
                client.reconnect()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                log.info("Message $token delivery successful")
            }
        })
        client.connect(options)
        client.subscribe("$pullTopic/#", 2)
        log.info("Listening on $pullTopic/#")
    }

    override fun createReceive(): Receive {
        return ReceiveBuilder()
                .matchAny { client.publish(pushTopic, "$it from ${sender.path()}".toByteArray(), qos, false) }
                .build()
    }
}
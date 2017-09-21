package core

import akka.actor.AbstractActor
import akka.actor.Props
import akka.japi.pf.ReceiveBuilder


class MqttActor: AbstractActor() {

    data class Message(val type: Type, val payload: Map<Any, Any>) {
        enum class Type {
            ALIVE
        }
    }

    companion object {
        fun props(): Props {
            return Props.create(MqttActor::class.java)
        }
    }

    override fun preStart() {
        super.preStart()

    }

    override fun createReceive(): Receive {
        return ReceiveBuilder()
                .match(Message::class.java) { m ->
                    println(m)
                }
                .build()
    }
}
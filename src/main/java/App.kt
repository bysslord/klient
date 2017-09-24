import akka.actor.ActorSystem
import akka.actor.Props
import core.ExecutorActor
import core.MqttActor


object Klient {
    @Throws(java.io.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val system = ActorSystem.create("Noah")
        system.actorOf(Props.create(MqttActor::class.java), "mqtt")
        system.actorOf(Props.create(ExecutorActor::class.java), "executor")
    }
}
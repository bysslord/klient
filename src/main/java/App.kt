import akka.actor.ActorRef
import core.remote
import core.system
import core.MqttActor


object Klient {
    @Throws(java.io.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        remote.tell(MqttActor.Message(MqttActor.Message.Type.ALIVE, mapOf(1 to 1)), ActorRef.noSender())
        System.`in`.read()
        system.terminate()
        System.`in`.read()
        System.exit(0)
    }
}
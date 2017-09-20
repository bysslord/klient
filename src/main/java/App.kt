import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props


internal class PrintActor : AbstractActor() {
    override fun createReceive(): Receive {
        return receiveBuilder()
                .matchEquals("printit") { p ->
                }
                .build()
    }
}

object Klient {
    @Throws(java.io.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val system = ActorSystem.create("testSystem")

        val printActor = system.actorOf(Props.create(PrintActor::class.java), "first-actor")
        println("First: " + printActor)
        printActor.tell("printit", ActorRef.noSender())

        system.terminate()
        System.exit(0)
    }
}
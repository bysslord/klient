package core

import akka.actor.AbstractActor
import org.apache.log4j.Logger

val log: Logger = Logger.getLogger("sailor")


open class VerboseActor: AbstractActor() {
    override fun createReceive(): Receive {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun preStart() {
        super.preStart()
        log.info("Actor ${self.path()} was started")
    }

    override fun postRestart(reason: Throwable?) {
        super.postRestart(reason)
        log.warn("Actor ${self.path()} was restarted by ${reason.toString()}")
    }

    override fun postStop() {
        super.postStop()
        log.info("Actor ${self.path()} was stopped")
    }
}

class Message(val string: String? = null, val map: Map<Any, Any>? = null) {
    override fun toString(): String {
        return ""
    }
}
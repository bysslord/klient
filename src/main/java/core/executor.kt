package core

class ExecutorActor: VerboseActor() {
    override fun createReceive(): Receive {
        return receiveBuilder()
                .matchAny {
                    println(it)
                    sender.tell("received", self)
                }
                .build()
    }
}
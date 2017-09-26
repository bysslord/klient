package core

class ExecutorActor: VerboseActor() {
    override fun createReceive(): Receive {
        return receiveBuilder()
                .matchAny {
                    sender.tell("received", self)
                    context.system.terminate()
                    System.exit(0)
                }
                .build()
    }
}
package core

import akka.actor.ActorRef
import akka.actor.ActorSystem
import java.nio.file.FileSystems

val system: ActorSystem = ActorSystem.create("Noah")
val remote: ActorRef = system.actorOf(MqttActor.props())
val cwd: String = FileSystems.getDefault().getPath(".").toString()
val clientId: String = "te"
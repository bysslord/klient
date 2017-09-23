package core

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import java.nio.file.FileSystems

val system: ActorSystem = ActorSystem.create("Noah")
val remote: ActorRef = system.actorOf(Props.create(MqttActor::class.java), "mqtt")
val cwd: String = FileSystems.getDefault().getPath(".").toString()
val clientId: String = "te"
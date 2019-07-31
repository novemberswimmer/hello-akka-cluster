package org.november.learning.akka.cluster.singleton

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import org.november.learning.akka.cluster.singleton.Endpoint.Hello

object Endpoint{
  def props: Props = Props(new Endpoint)

  case class Hello(helloWhat: String)
}

class Endpoint extends Actor with ActorLogging{
  var ctr: Int = 0;

  override def receive: Receive = LoggingReceive {
    case h: Hello => {
      ctr += 1
      val whosent = sender()
      println(s"[Endpoint] Hello ${h.helloWhat} from ${whosent} Counter count is ${ctr}")
    }
    case other => println(s"Unexpected message ${other}")
  }
}

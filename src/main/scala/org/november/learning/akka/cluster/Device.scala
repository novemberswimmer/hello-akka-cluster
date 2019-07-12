package org.november.learning.akka.cluster

import akka.actor.{Actor, ActorLogging, Props}
import org.november.learning.akka.cluster.Device.RecordTemperature

object Device{
  case class RecordTemperature(deviceId: Int, temperature: Double)

  def props: Props = Props(new Device)
}
class Device extends Actor with ActorLogging{

  override def receive: Receive = {
    case rt @ RecordTemperature(deviceId, temperature) =>
      log.info(s"Received Record Temperature ${rt}")

    case other => log.info(s"Received unknown message ${other}")
  }
}

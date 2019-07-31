package org.november.learning.akka.cluster.sharding

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import Devices.UpdateDevice

import scala.util.Random

object Devices {
  case object UpdateDevice
  def props: Props = Props(new Devices)
}

class Devices extends Actor with ActorLogging{


  private val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg @ Device.RecordTemperature(id,_) => (id.toString, msg)
  }

  private val extractShardId: ShardRegion.ExtractShardId ={
    case Device.RecordTemperature(id,_) =>
      val actor_id = id % numberOfDevices
      println(s"Actor_id :: ${actor_id}")
      "1"
  }

  val deviceRegion: ActorRef = ClusterSharding(context.system).start(
    typeName = "Device",
    entityProps = Device.props,
    settings = ClusterShardingSettings(context.system),
    extractEntityId = extractEntityId,
    extractShardId = extractShardId
  )


  val random = new Random()
  val numberOfDevices = 50

  override def receive: Receive = {
    case UpdateDevice =>
      val deviceId = random.nextInt(numberOfDevices)
      val temperature = 5 + 30 * random.nextDouble()
      val msg = Device.RecordTemperature(2, temperature)
      log.info(s"Sending $msg");
      deviceRegion ! msg
      deviceRegion ! msg.copy(deviceId = 3)
      deviceRegion ! msg
  }
}

package org.november.learning.akka.cluster

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import org.november.learning.akka.cluster.Devices.UpdateDevice

import scala.util.Random

object Devices {
  case object UpdateDevice
  def props: Props = Props(new Devices)
}

class Devices extends Actor with ActorLogging{

  /*
   private val extractShardId: ShardRegion.ExtractShardId = {
    case Device.RecordTemperature(id, _) => (id % numberOfShards).toString
    // Needed if you want to use 'remember entities':
    //case ShardRegion.StartEntity(id) => (id.toLong % numberOfShards).toString
  }
  * */

  private val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg @ Device.RecordTemperature(id,_) => (id.toString, msg)
  }

  private val extractShardId: ShardRegion.ExtractShardId ={
    case Device.RecordTemperature(id,_) =>
      val actor_id = id % numberOfDevices
      (actor_id).toString
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
      val msg = Device.RecordTemperature(deviceId, temperature)
      log.info(s"Sending $msg");
      deviceRegion ! msg
  }
}

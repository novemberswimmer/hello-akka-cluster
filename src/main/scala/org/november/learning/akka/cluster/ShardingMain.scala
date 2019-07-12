package org.november.learning.akka.cluster

import akka.actor.ActorSystem

object ShardingMain extends App {

  val system = ActorSystem("cluster-sharding-learning")

  val devices = system.actorOf(Devices.props)

  devices ! Devices.UpdateDevice
}

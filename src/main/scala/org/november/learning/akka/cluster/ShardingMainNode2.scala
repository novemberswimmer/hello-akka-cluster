package org.november.learning.akka.cluster

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object ShardingMainNode2 extends App{

  //Override the value of property akka.remote.artery.canonical.port in application.conf
  val config = ConfigFactory.parseString(s"akka.remote.artery.canonical.port=2552")
    .withFallback(ConfigFactory.load())

  /*Create an actor system with configuration coming from config variable that have the orginal content
  of application.conf plus the property that was overriden programmatically
   */
  val system = ActorSystem("cluster-sharding-learning", config)

}

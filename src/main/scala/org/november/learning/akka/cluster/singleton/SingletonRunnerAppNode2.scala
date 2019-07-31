package org.november.learning.akka.cluster.singleton

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings, ClusterSingletonProxy, ClusterSingletonProxySettings}
import com.typesafe.config.ConfigFactory
import org.november.learning.akka.cluster.singleton.Endpoint.Hello
import scala.concurrent.duration._

object SingletonRunnerAppNode2 extends App {

  var endPointSingleton: ActorRef=null;
  val config = ConfigFactory.parseString("akka.remote.artery.canonical.port=" + "2552").
    withFallback(ConfigFactory.load("application-singleton.conf"))

  val system = ActorSystem("singleton-example", config)
  implicit  val ec = system.dispatcher

  val singletonProps: Props = ClusterSingletonManager.props(Endpoint.props, PoisonPill,
    ClusterSingletonManagerSettings.create(system))

  val singletonManager: ActorRef = system.actorOf(singletonProps,"singleton-manager")

  val proxySettings: ClusterSingletonProxySettings = ClusterSingletonProxySettings.create(system)

  val clusterSingletonProxy: ActorRef = system.actorOf(ClusterSingletonProxy.props(singletonManager.path.toStringWithoutAddress, proxySettings),
  "cluster-singleton-proxy")

  system.scheduler.schedule(5.seconds, 5.second,  clusterSingletonProxy, new Hello("World from node2"))

}

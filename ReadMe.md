#Learning AKKA Cluster

## Configuration
* ShardingMainNode2 app demonstrate how to override a property in application.conf and pass that
overriden property value during the creation of the actor system.
* Without overriding the default value of property ```akka.remote.artery.canonical.port ```
you will get the error below if ShardingMain app is already running.  You will also get the same error
when you run ShardingMain app while ShardingMainNode2 is already running.
```scala
xception in thread "main" akka.remote.RemoteTransportException: Failed to bind TCP to [127.0.0.1:2551] due to: Bind failed because of java.net.BindException: Address already in use[ERROR] [07/12/2019 07:51:36.723] [cluster-sharding-learning-akka.actor.default-dispatcher-4] [akka://cluster-sharding-learning@127.0.0.1:2551/system/IO-TCP/selectors/$a/0] Bind failed for TCP channel on endpoint [/127.0.0.1:2551]
```

## Clustering
* If you start the app ShardingMainNode2 without the app ShardingMain 1st running you will get the error below
this is because as per configuration in ```application.conf``` The leader node will be running on port 2551 (which is the port use by ShardingMain app)
```hocon
cluster {
    seed-nodes = [
      "akka://cluster-sharding-learning@127.0.0.1:2551"]}
```
```scala
 Cluster Node [akka://cluster-sharding-learning@127.0.0.1:2552] - Couldn't join seed nodes after [2] attempts, will try again. seed-nodes=[akka://cluster-sharding-learning@127.0.0.1:2551]
 ```
 * If you have the two app ShardingMain and ShardingMainNode2 create an actor system with different name
you will see this warning in the log, because it expects that nodes in the same AKKA cluster have the same ActorSystem anme
```scala
Cluster Node [akka://cluster-sharding-learning-node2@127.0.0.1:2552] - Trying to join member with wrong ActorSystem name, but was ignored, expected [cluster-sharding-learning-node2] but was [cluster-sharding-learning]
```
* Successful clustering to a seed node will be mark by a log entry below
```scala
Cluster Node [akka://cluster-sharding-learning@127.0.0.1:2552] - Welcome from [akka://cluster-sharding-learning@127.0.0.1:2551]
```
name := "hello-akka-cluster"

version := "0.1"

scalaVersion := "2.12.8"

val akkaVersion = "2.5.22"

resolvers += "Lightbend Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.2"
)
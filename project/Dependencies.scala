import DependencyVersions._
import sbt.SbtExclusionRule
import sbt._

object Dependencies {
  val globalExcludes = Seq(
    SbtExclusionRule("log4j"),
    SbtExclusionRule("log4j2"),
    SbtExclusionRule("commons-logging")
  )

  val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
    "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  )

  val akkaHttpDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-core" % json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % akkaJson4sVersion
  )

  val spdfDependencies = Seq(
    "io.github.cloudify" %% "spdf" % spdfVersion
  )

  val testDependencies = Seq(
    "org.scalactic" %% "scalactic" % scalaTestVersion % "test",
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test"
  )

  val projectDependencies = Seq(
    loggingDependencies,
    akkaHttpDependencies,
    spdfDependencies,
    testDependencies
  ).reduce(_ ++ _)

  val additionalResolvers = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  )
}

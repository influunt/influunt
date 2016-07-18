import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

name := """72c-os"""

val akkaVersion = "2.4.7"

val project = Project(
  id = "os72c",
  base = file(".")
)
  .settings(SbtMultiJvm.multiJvmSettings: _*)
  .settings(
    name := """akka-sample-cluster-java""",
    version := "2.4.4",
    scalaVersion := "2.11.7",
    scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"),
    javacOptions in doc in Compile := Seq("-source", "1.8", "-Xdoclint:none"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
      "org.iq80.leveldb" % "leveldb" % "0.7",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
      "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "com.typesafe" % "config" % "1.3.0",
//      "io.kamon" % "sigar-loader" % "1.6.6-rev002",
      "org.scream3r" % "jssc" % "2.8.0",
      "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.1.0",
      "org.fusesource.mqtt-client" % "mqtt-client" % "1.14",
      "com.google.guava" % "guava" % "19.0"),
    javaOptions in run ++= Seq(
      "-Xms128m", "-Xmx1024m", "-Djava.library.path=./target/native"),
    Keys.fork in run := true,
    // make sure that MultiJvm test are compiled by the default test compilation
    compile in MultiJvm <<= (compile in MultiJvm) triggeredBy (compile in Test),
    // disable parallel tests
    parallelExecution in Test := false,
    // make sure that MultiJvm tests are executed by the default test target,
    // and combine the results from ordinary test and multi-jvm tests
    executeTests in Test <<= (executeTests in Test, executeTests in MultiJvm) map {
      case (testResults, multiNodeResults)  =>
        val overall =
          if (testResults.overall.id < multiNodeResults.overall.id)
            multiNodeResults.overall
          else
            testResults.overall
        Tests.Output(overall,
          testResults.events ++ multiNodeResults.events,
          testResults.summaries ++ multiNodeResults.summaries)
    },
    licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
  )
  .configs (MultiJvm)

mainClass in Compile := Some("os72c.client.Client")

import NativePackagerHelper._

enablePlugins(JavaServerAppPackaging)

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
    // copy configuration files to config directory
    contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

scriptClasspath := Seq("../config/") ++ scriptClasspath.value
//
//name := """72c-os"""
//
//version := "1.0"
//
//scalaVersion := "2.11.6"
//
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
//  "com.typesafe" % "config" % "1.3.0",
//  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
//  "junit" % "junit" % "4.12" % "test",
//  "com.novocode" % "junit-interface" % "0.11" % "test",
//  "org.scream3r" % "jssc" % "2.8.0",
//  "com.google.guava" % "guava" % "19.0")
//
//
//
//mainClass in Compile := Some("os72c.client.Client")
//
//import NativePackagerHelper._
//
//enablePlugins(JavaServerAppPackaging)
//
//mappings in Universal ++= {
//  // optional example illustrating how to copy additional directory
//  directory("scripts") ++
//    // copy configuration files to config directory
//    contentOf("src/main/resources").toMap.mapValues("config/" + _)
//}
//
//scriptClasspath := Seq("../config/") ++ scriptClasspath.value
//
//

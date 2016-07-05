
name := """72c-os"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "org.scream3r" % "jssc" % "2.8.0",
  "com.google.guava" % "guava" % "19.0")



mainClass in Compile := Some("os72c.Application")

import NativePackagerHelper._

enablePlugins(JavaServerAppPackaging)

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
    // copy configuration files to config directory
    contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

scriptClasspath := Seq("../config/") ++ scriptClasspath.value
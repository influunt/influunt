
name := """influunt-simulador"""

version := "0.1.0"
val akkaVersion = "2.4.7"
scalaVersion := "2.11.7"

resolvers += Resolver.jcenterRepo
javaOptions in Test += "-Dconfig.file=conf/test.conf"
javaOptions in Test += "-Dtest.timeout=600000"



lazy val influuntSimulador = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
        .dependsOn(influuntCore)
        .aggregate(influuntCore)
  .settings(
    name := """influunt-central""",
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    sources in (Compile,doc) := Seq.empty,
    version := "2.4.4",
    scalaVersion := "2.11.7",
    scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"),
    javacOptions in doc in Compile := Seq("-source", "1.8", "-Xdoclint:none"),
    libraryDependencies ++= Seq(
      javaJdbc,
      cache,
      javaWs,
      evolutions,
      "be.objectify" %% "deadbolt-java" % "2.5.0",
      "mysql" % "mysql-connector-java" % "5.1.36",
      "org.hibernate" % "hibernate-validator" % "5.2.4.Final",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.7.5",
      "uk.co.modular-it" % "bean-utils" % "0.9.10",
      "org.hamcrest" % "hamcrest-library" % "1.3",
      "commons-beanutils" % "commons-beanutils" % "1.9.2",
      "net.coobird" % "thumbnailator" % "0.4.8",
      "org.mindrot" % "jbcrypt" % "0.3m",
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
      "com.google.code.gson" % "gson" % "2.7",
      "com.google.guava" % "guava" % "19.0"),
    javaOptions in run ++= Seq(
      "-Xms128m", "-Xmx1024m", "-Djava.library.path=./target/native"),
    // disable parallel tests
    parallelExecution in Test := false
    // make sure that MultiJvm tests are executed by the default test target,
    // and combine the results from ordinary test and multi-jvm tests
  )

lazy val influuntCore = (project in file("../influunt-core")).enablePlugins(PlayJava, PlayEbean)



mainClass in Compile := Some("os72c.client.Client")

import NativePackagerHelper._

enablePlugins(JavaServerAppPackaging)
daemonUser in Debian := "root"

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
    // copy configuration files to config directory
    contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

scriptClasspath := Seq("../config/") ++ scriptClasspath.value

maintainer := "Josh Suereth <joshua.suereth@typesafe.com>"

packageSummary := "Test debian package"

packageDescription := """A fun package description of our software,
  with multiple lines."""

requiredStartFacilities := Some("$test-service")

requiredStartFacilities in Debian := Some("$test-deb-service")

termTimeout in Debian := 10

killTimeout in Debian := 20

TaskKey[Unit]("check-control-files") <<= (target, streams) map { (target, out) =>
  val header = "#!/bin/sh"
  val debian = target / "debian-test-0.1.0" / "DEBIAN"
  val postinst = IO.read(debian / "postinst")
  val postrm = IO.read(debian / "postrm")
  Seq(postinst, postrm) foreach { script =>
    assert(script.startsWith(header), "script doesn't start with #!/bin/sh header:\n" + script)
    assert(header.r.findAllIn(script).length == 1, "script contains more than one header line:\n" + script)
  }
  out.log.success("Successfully tested systemV control files")
  ()
}

TaskKey[Unit]("check-startup-script") <<= (target, streams) map { (target, out) =>
  val script = IO.read(target / "debian-test-0.1.0" / "etc" / "init.d" / "debian-test")
  assert(script.contains("# Default-Start: 2 3 4 5"), "script doesn't contain Default-Start header\n" + script)
  assert(script.contains("# Default-Stop: 0 1 6"), "script doesn't contain Default-Stop header\n" + script)
  assert(script.contains("# Required-Start: $test-deb-service"), "script doesn't contain Required-Start header\n" + script)
  assert(script.contains("# Required-Stop: $remote_fs $syslog"), "script doesn't contain Required-Stop header\n" + script)
  assert(script.contains("--retry=TERM/10/KILL/20"), "script doesn't contains stop timeouts\n" + script)
  out.log.success("Successfully tested systemV start up script")
  ()
}

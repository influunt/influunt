

name := """influunt-api"""

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore,influuntDevice)
    .aggregate(influuntCore,influuntDevice)

lazy val influuntDevice = (project in file("modules/influunt-device")).enablePlugins(PlayJava, PlayEbean)
     .dependsOn(influuntCore)
     .aggregate(influuntCore)

lazy val influuntCore = (project in file("modules/influunt-core")).enablePlugins(PlayJava, PlayEbean)


scalaVersion := "2.11.7"
resolvers += Resolver.jcenterRepo
javaOptions in Test += "-Dconfig.file=conf/test.conf"
javaOptions in Test += "-Dtest.timeout=600000"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
  "be.objectify" %% "deadbolt-java" % "2.5.0",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.hibernate" % "hibernate-validator" % "5.2.4.Final",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.7.5",
  "uk.co.modular-it" % "bean-utils" % "0.9.10",
  "org.hamcrest" % "hamcrest-library" % "1.3",
  "commons-beanutils" % "commons-beanutils" % "1.9.2",
  "net.coobird" % "thumbnailator" % "0.4.8",
  "org.mindrot" % "jbcrypt" % "0.3m")

jacoco.settings
parallelExecution in jacoco.Config := false
jacoco.reportFormats in jacoco.Config := Seq(
  de.johoop.jacoco4sbt.ScalaHTMLReport(encoding = "utf-8", withBranchCoverage = true),
  de.johoop.jacoco4sbt.XMLReport(encoding = "utf-8"))


fork in run := false

fork in Test := false
resolvers ++= Seq(Resolver.mavenLocal, "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")

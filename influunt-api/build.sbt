name := """influunt-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"
resolvers += Resolver.jcenterRepo
javaOptions in Test += "-Dconfig.file=conf/test.conf"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  evolutions,  
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "be.objectify" %% "deadbolt-java" % "2.5.0", 
  "mysql" % "mysql-connector-java" % "5.1.36"
)

jacoco.settings
parallelExecution in jacoco.Config := false
jacoco.reportFormats in jacoco.Config := Seq(
  de.johoop.jacoco4sbt.XMLReport(encoding = "utf-8"))

  
fork in run := false

fork in Test := false


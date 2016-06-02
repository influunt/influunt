name := """influunt-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  evolutions,  
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "be.objectify" %% "deadbolt-java" % "2.5.0" 
)

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources) 
EclipseKeys.preTasks := Seq(compile in Compile)

jacoco.settings
jacoco.reportFormats in jacoco.Config := Seq(
  de.johoop.jacoco4sbt.XMLReport(encoding = "utf-8"))

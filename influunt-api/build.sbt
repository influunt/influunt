name := """influunt-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava).dependsOn(swagger)
lazy val swagger = RootProject(uri("https://github.com/CreditCardsCom/swagger-play.git"))

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  evolutions,  
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "be.objectify" %% "deadbolt-java" % "2.5.0" ,
  "io.swagger" %% "swagger-play2" % "1.5.2-SNAPSHOT"
)

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources) 
EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.skipProject in swagger := true
jacoco.settings
jacoco.reportFormats in jacoco.Config := Seq(
  de.johoop.jacoco4sbt.XMLReport(encoding = "utf-8"))



name := """influunt"""

version := "0.1.0"


lazy val influuntCore = (project in file("modules/influunt-core")).enablePlugins(PlayJava, PlayEbean)

lazy val influuntCentral = (project in file("modules/influunt-central")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore)
    .aggregate(influuntCore)

lazy val influuntSimulador = (project in file("modules/influunt-simulador")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore)
    .aggregate(influuntCore)


lazy val influuntDevice = (project in file("modules/influunt-device")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore)
    .aggregate(influuntCore)

lazy val influuntApi = (project in file("modules/influunt-api")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore, influuntSimulador)
    .aggregate(influuntCore, influuntSimulador)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore, influuntDevice, influuntCentral, influuntSimulador, influuntApi)
    .aggregate(influuntCore, influuntDevice, influuntCentral, influuntSimulador, influuntApi)



scalaVersion := "2.11.7"
resolvers += Resolver.jcenterRepo
javaOptions in Test += "-Dconfig.file=conf/test.conf"
javaOptions in Test += "-Dtest.timeout=600000"



libraryDependencies ++= Seq(
    javaJdbc,
    cache,
    javaWs,
    "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
    //  "info.cukes" % "cucumber-junit" % "1.2.5" % "test",
    //  "info.cukes" % "cucumber-java" % "1.2.5" % "test",
    //  "info.cukes" % "cucumber-guice" % "1.2.5" % "test",
    "be.objectify" %% "deadbolt-java" % "2.5.0",
    "mysql" % "mysql-connector-java" % "5.1.36",
    "org.hibernate" % "hibernate-validator" % "5.2.4.Final",
    "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.7.5",
    "uk.co.modular-it" % "bean-utils" % "0.9.10",
    "org.hamcrest" % "hamcrest-library" % "1.3",
    "commons-beanutils" % "commons-beanutils" % "1.9.2",
    "net.coobird" % "thumbnailator" % "0.4.8",
    "io.moquette" % "moquette-broker" % "0.8.1" exclude("org.slf4j", "slf4j-log4j12"),
    "org.mindrot" % "jbcrypt" % "0.3m",
    "org.apache.commons" % "commons-math3" % "3.6.1",
    "com.typesafe.play" %% "play-mailer" % "5.0.0",
    "org.thymeleaf" % "thymeleaf" % "3.0.1.RELEASE",
    "org.jetbrains.kotlin" % "kotlin-stdlib" % "1.0.3",
    "org.eclipse.collections" % "eclipse-collections-api" % "7.1.0",
    "org.eclipse.collections" % "eclipse-collections" % "7.1.0",
    "org.eclipse.collections" % "eclipse-collections-forkjoin" % "7.1.0",
    "net.jpountz.lz4" % "lz4" % "1.3.0",
    "org.mapdb" % "elsa" % "3.0.0-M6",
    "com.google.guava" % "guava" % "19.0",
    "org.jfree" % "jfreesvg" % "3.1",
    "net.sf.jasperreports" % "jasperreports" % "6.3.1",
    "com.github.jhonnymertz" % "java-wkhtmltopdf-wrapper" % "1.0.1-RELEASE",
    "org.awaitility" % "awaitility-scala" % "2.0.0",
//    "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.1.0",
    "org.fusesource.mqtt-client" % "mqtt-client" % "1.14")


jacoco.settings
parallelExecution in jacoco.Config := false
jacoco.reportFormats in jacoco.Config := Seq(
    de.johoop.jacoco4sbt.ScalaHTMLReport(encoding = "utf-8", withBranchCoverage = true),
    de.johoop.jacoco4sbt.XMLReport(encoding = "utf-8"))


fork in run := false

fork in Test := false
resolvers in ThisBuild ++= Seq(Resolver.mavenLocal, "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")
resolvers in ThisBuild ++= Seq("Sonatype snapshots repository 2" at "http://dl.bintray.com/andsel/maven/")
resolvers in ThisBuild ++= Seq("Jasper" at "http://jasperreports.sourceforge.net/maven2/")
resolvers in ThisBuild ++= Seq("Jasper Third Party" at "http://jaspersoft.artifactoryonline.com/jaspersoft/third-party-ce-artifacts/")
resolvers in ThisBuild ++= Seq("Java PDF" at "https://jitpack.io")

mappings in Universal <++= (packageBin in Compile) map { jar =>
    val scriptsDir = new java.io.File("app/templates/")
    scriptsDir.listFiles.toSeq.map { f =>
        f -> ("/app/templates/" + f.getName)
    }
}

mappings in Universal <++= (packageBin in Compile) map { jar =>
    val scriptsDir = new java.io.File("app/templates/reports/")
    scriptsDir.listFiles.toSeq.map { f =>
        f -> ("app/templates/reports/" + f.getName)
    }
}

mappings in Universal <++= (packageBin in Compile) map { jar =>
    val scriptsDir = new java.io.File("public/images/")
    scriptsDir.listFiles.toSeq.map { f =>
        f -> ("public/images/" + f.getName)
    }
}

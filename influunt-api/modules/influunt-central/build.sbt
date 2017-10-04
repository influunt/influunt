name := """influunt-central"""
version := "0.1.0"
scalaVersion := "2.11.7"

resolvers += Resolver.jcenterRepo
javaOptions in Test += "-Dconfig.file=conf/test.conf"
javaOptions in Test += "-Dtest.timeout=600000"

lazy val influuntCore = (project in file("../influunt-core")).enablePlugins(PlayJava, PlayEbean)

lazy val influuntCentral = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
    .dependsOn(influuntCore)
    .aggregate(influuntCore)
    .settings(
        name := """influunt-central""",
        publishArtifact in(Compile, packageDoc) := false,
        publishArtifact in packageDoc := false,
        sources in(Compile, doc) := Seq.empty,
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
            "com.novocode" % "junit-interface" % "0.11" % "test",
            "com.typesafe" % "config" % "1.3.0",
            "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.1.0",
            "com.google.code.gson" % "gson" % "2.7",
            "com.google.guava" % "guava" % "19.0"),
        javaOptions in run ++= Seq(
            "-Xms128m", "-Xmx1024m", "-Djava.library.path=./target/native"),
        parallelExecution in Test := false
    )


mappings in Universal ++= Seq(new java.io.File("modules/influunt-central/conf/central.conf") -> "conf/application.conf")
mappings in Universal ++= Seq(new java.io.File("conf/ebean.properties") -> "conf/ebean.properties")
mappings in Universal <++= (packageBin in Compile) map { jar =>
    val scriptsDir = new java.io.File("conf/evolutions/default")
    scriptsDir.listFiles.toSeq.map { f =>
        f -> ("conf/evolutions/default/" + f.getName)
    }
}


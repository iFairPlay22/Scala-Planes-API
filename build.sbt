// Projects
lazy val global = (project in file("."))
  .settings(defaultSettings)
  .settings(
    name := "planes-root",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= commonsLibraryDependencies,
    Test / parallelExecution := false,
    publish / skip := true)
  .aggregate(domain, brokerConsumer, brokerProducer, db, api)
  .dependsOn(domain, brokerConsumer, brokerProducer, db, api)

lazy val api = (project in file("api"))
  .enablePlugins(DockerPlugin, JavaAppPackaging)
  .settings(defaultSettings)
  .settings(dockerSettings)
  .settings(
    name := "planes-api",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= apiLibraryDependencies,
    Compile / run / mainClass := Some("api.Main"),
    Docker / packageName := "planes-api")
  .dependsOn(domain, db)

lazy val brokerConsumer = (project in file("broker-consumer"))
  .enablePlugins(DockerPlugin, JavaAppPackaging)
  .settings(defaultSettings)
  .settings(dockerSettings)
  .settings(
    name := "planes-broker-consumer",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= brokerConsumerLibraryDependencies,
    Compile / run / mainClass := Some("broker_consumer.Main"),
    Docker / packageName := "planes-broker-consumer")
  .dependsOn(domain, db)

lazy val brokerProducer = (project in file("broker-producer"))
  .enablePlugins(DockerPlugin, JavaAppPackaging)
  .settings(defaultSettings)
  .settings(dockerSettings)
  .settings(
    name := "planes-broker-producer",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= brokerProducerLibraryDependencies,
    Compile / run / mainClass := Some("broker_producer.Main"),
    Docker / packageName := "planes-broker-producer")
  .dependsOn(domain)

lazy val db = (project in file("db"))
  .settings(defaultSettings)
  .settings(
    name := "planes-database",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= dbLibraryDependencies,
    Compile / run / mainClass := Some("database.Main"))
  .dependsOn(domain)

lazy val domain = (project in file("domain"))
  .settings(defaultSettings)
  .settings(
    name := "planes-domain",
    scalaVersion := projectLibraryDependencies.scala.scalaVersion,
    libraryDependencies ++= domainLibraryDependencies)
  .dependsOn()

// Default settings
lazy val defaultSettings = Seq(
  organization := "ewenbouquet",
  publishTo := {
    val nexus =
      sys.env.getOrElse("NEXUS_BASE_URL", "https://ewenbouquet-nexus-public-url.loca.lt")
    if (isSnapshot.value)
      Some("snapshots" at nexus + "/repository/maven-snapshots/")
    else
      Some("releases" at nexus + "/repository/maven-releases/")
  },
  credentials += Credentials(Path.userHome / ".sbt" / ".ewenbouquet_credentials"))

// Docker plugin settings
lazy val dockerSettings =
  Seq(dockerUsername := Some("ewenbouquet"), dockerBaseImage := "openjdk:11")

// Library dependencies
lazy val projectLibraryDependencies =
  new {

    val scala = new {
      val scalaVersion = "2.13.10"
    }

    val ewenbouquet = new {
      val commonsVersion = "0.1.0-SNAPSHOT"

      val commonsBase = "ewenbouquet" %% "commons-commons-libs" % commonsVersion
      val commonsBroker = "ewenbouquet" %% "commons-broker-libs" % commonsVersion
      val commonsDb = "ewenbouquet" %% "commons-db-libs" % commonsVersion
      val commonsHttp = "ewenbouquet" %% "commons-http-libs" % commonsVersion
      val commonsScheduler = "ewenbouquet" %% "commons-scheduler-libs" % commonsVersion
    }

  }

lazy val commonsLibraryDependencies =
  Seq()

lazy val apiLibraryDependencies =
  commonsLibraryDependencies ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsHttp)

lazy val brokerConsumerLibraryDependencies =
  commonsLibraryDependencies ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsBroker)

lazy val brokerProducerLibraryDependencies =
  commonsLibraryDependencies ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsBroker) ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsScheduler)

lazy val dbLibraryDependencies =
  commonsLibraryDependencies ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsDb)

lazy val domainLibraryDependencies =
  commonsLibraryDependencies ++
    Seq(projectLibraryDependencies.ewenbouquet.commonsBase)

resolvers ++= Resolver.sonatypeOssRepos("public")

lazy val logBackVersion           = "1.2.10"
lazy val calibanVersion           = "2.0.2"
lazy val zioVersion               = "2.0.0"
lazy val tapirVersion             = "1.2.0"
lazy val http4sBlazeServerVersion = "0.23.12"

lazy val root = (project in file("."))
  .settings(
    resolvers += "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.13.10"
      )
    ),
    name := "Example",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-core"              % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"     % tapirVersion,
      "org.http4s"                  %% "http4s-blaze-server"     % http4sBlazeServerVersion,
      "com.github.ghostdogpr"       %% "caliban-tapir"           % calibanVersion,
      "com.github.ghostdogpr"       %% "caliban"                 % calibanVersion,
      "dev.zio"                     %% "zio"                     % zioVersion,
      "ch.qos.logback"               % "logback-classic"         % logBackVersion
    ),
    scalacOptions ++= Seq(
      "-language:implicitConversions",
      "-Vimplicits",
      "-Vtype-diffs",
      "-Xlint",
      "-Wconf:cat=lint-byname-implicit:silent",
      "-Wunused:params",
      "-Wunused:nowarn",
      "-Xfatal-warnings",
      "-Ybackend-parallelism",
      "8",
      "-Ymacro-annotations"
    )
  )

Compile / run / fork := true

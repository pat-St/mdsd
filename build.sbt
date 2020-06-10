name := """Mdsd"""
organization := "none"

version := "1.0-SNAPSHOT"
val dottyVersion = "0.24.0-RC1"
val playVersion = "2.8.1"
lazy val root = project
  .in(file("."))
  .enablePlugins(play.sbt.PlayScala)
  .settings(
    version := "0.1.0",
    scalaVersion := dottyVersion,
    libraryDependencies ++= Seq(
      ("com.typesafe.play" %% "play-netty-server" % playVersion),
      ("com.typesafe.play" %% "play-json" % playVersion),
      ("org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test),
      ("net.codingwell" %% "scala-guice" % "4.2.7"),
      guice
      //("org.scalatest" %% "scalatest" % "3.2.0-M4" % Test),
      //("com.typesafe.play" %% "play-test" % playVersion % Test),
      //("com.typesafe.play" %% "play-ahc-ws" % playVersion % Test),
      //("com.novocode" % "junit-interface" % "0.11" % Test),
    ),
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(dottyVersion)),
  )
  .enablePlugins(SbtTwirl)
scalacOptions ++= { if (isDotty.value) Seq("-language:Scala2Compat","-language:implicitConversions") else Nil }
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "zetra.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "zetra.binders._"

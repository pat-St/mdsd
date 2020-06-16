name := """Mdsd"""
organization := "none"

version := "1.0-SNAPSHOT"
val dottyVersion = "0.25.0-RC2"
val playVersion = "2.8.2"
lazy val root = project
  .in(file("."))
  .enablePlugins(play.sbt.PlayScala)
  .settings(
    version := "0.1.0",
    scalaVersion := dottyVersion,
    libraryDependencies ++= Seq(
      ("com.typesafe.play" %% "play-netty-server" % playVersion),
      ("com.typesafe.play" %% "play-json" % "2.9.0"),
      ("net.codingwell" %% "scala-guice" % "4.2.7"),
      ("org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"),
      ("org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test),
      //("com.novocode" % "junit-interface" % "0.11" % Test),
      guice
    ),
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(dottyVersion)),
  )
  .enablePlugins(SbtTwirl)
scalacOptions ++= { if (isDotty.value) Seq("-language:Scala2Compat","-language:implicitConversions") else Nil }
// Adds additional packages into Twirl
TwirlKeys.templateImports += "model._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "none.model._"

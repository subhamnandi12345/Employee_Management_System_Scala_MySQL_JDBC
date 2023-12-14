name := """employeejdbc"""
organization := "com.employeejdbc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.20"
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.employeejdbc.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.employeejdbc.binders._"

name := """storyBoard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)


libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1"
libraryDependencies += "com.h2database" % "h2" % "1.4.191"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

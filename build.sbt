name := """scalaTest3"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.2.7",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.2.7",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.4.0",
  jdbc,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  cache,
  evolutions,
  "mysql" % "mysql-connector-java" % "5.1.24",
  "com.h2database"  %  "h2"                % "1.4.188",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.3",
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

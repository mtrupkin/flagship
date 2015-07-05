name := """flagship"""

version := "1.0-SNAPSHOT"

organization := "me.mtrupkin"

scalaVersion := "2.11.6"

resolvers ++= Seq(
	Resolver.url("me.mtrupkin ivy repo", url("http://dl.bintray.com/mtrupkin/ivy/"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  "me.mtrupkin.console" %% "console-fx" % "1.0-SNAPSHOT",
  "me.mtrupkin.console" %% "console-core" % "0.8-SNAPSHOT"
)

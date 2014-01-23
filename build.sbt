import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "test-specs2-matchers"

organization := "com.franklinchen"

organizationHomepage := Some(url("http://franklinchen.com/"))

homepage := Some(url("http://github.com/FranklinChen/test-specs2-matchers"))

startYear := Some(2014)

description := "A Scala project"

version := "1.0.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.3" % "test",
  "org.specs2" %% "specs2" % "2.3.7" % "test"
)

org.scalastyle.sbt.ScalastylePlugin.Settings

jacoco.settings

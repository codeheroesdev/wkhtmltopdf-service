import com.typesafe.sbt.packager.docker._

name := "wkhtmltopdf-service"
version := "0.1"
scalaVersion := "2.12.3"

enablePlugins(SbtNativePackager)
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

packageName in Docker := "codeheroes/wkhtmltopdf-service"
maintainer in Docker := "codeheroes"
version in Docker := "1.0"
dockerBaseImage := "codeheroes/wkhtmltopdf-java8:1.0"
daemonUser in Docker := "root"
dockerExposedPorts := Seq(8080)
dockerCommands += Cmd("ENV", "QT_XKB_CONFIG_ROOT", "/usr/share/X11/xkb")

lazy val `wkhtmltopdf-service` = project.in(file("."))
  .settings(resolvers ++= Dependencies.additionalResolvers)
  .settings(excludeDependencies ++= Dependencies.globalExcludes)
  .settings(libraryDependencies ++= Dependencies.projectDependencies)




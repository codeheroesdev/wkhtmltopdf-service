name := "wkhtmltopdf-service"
version := "0.1"
scalaVersion := "2.12.3"

enablePlugins(SbtNativePackager)
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

lazy val `wkhtmltopdf-service` = project.in(file("."))
  .settings(resolvers ++= Dependencies.additionalResolvers)
  .settings(excludeDependencies ++= Dependencies.globalExcludes)
  .settings(libraryDependencies ++= Dependencies.projectDependencies)

package io.codeheroes.htmltopdf.api.core

import io.github.cloudify.scala.spdf.{Landscape, PageOrientation, Portrait}
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

object DomainFormatters {

  val all = List(LandscapeSerializer)

  implicit object LandscapeSerializer extends CustomSerializer[PageOrientation](_ => ({
    case JString("landscape") => Landscape
    case JString("portrait") => Portrait
  }, {
    case Landscape => JString("landscape")
    case Portrait => JString("portrait")
  }))
}

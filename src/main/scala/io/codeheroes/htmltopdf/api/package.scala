package io.codeheroes.htmltopdf


import io.codeheroes.htmltopdf.domain.Margin
import io.github.cloudify.scala.spdf.PageOrientation

package object api {
  final case class GeneratePDFRequest(orientation: PageOrientation, pageSize: String, margin: Margin, content: String)
}

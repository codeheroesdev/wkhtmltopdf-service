package io.codeheroes.htmltopdf.domain

import java.io.File

import io.github.cloudify.scala.spdf.PageOrientation

import scala.concurrent.{ExecutionContext, Future}

trait PDFService {
  def generatePdf(page: String, pageOrientation: PageOrientation, size: String, margin: Margin)(implicit ec: ExecutionContext): Future[File]
}

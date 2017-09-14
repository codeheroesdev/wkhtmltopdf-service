package io.codeheroes.htmltopdf.domain

import java.io.File

import io.github.cloudify.scala.spdf.PageOrientation

import scala.concurrent.Future


trait PDFService {
  def generatePdf(page: String, pageOrientation: PageOrientation, pageSize: String, margin: Margin): Future[File]
}

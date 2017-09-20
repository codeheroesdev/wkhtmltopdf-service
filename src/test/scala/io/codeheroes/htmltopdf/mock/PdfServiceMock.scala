package io.codeheroes.htmltopdf.mock

import java.io.File

import io.codeheroes.htmltopdf.domain.{Margin, PDFService}
import io.github.cloudify.scala.spdf.PageOrientation

import scala.concurrent.{ExecutionContext, Future}

class PdfServiceMock(filePath: String) extends PDFService {
  override def generatePdf(page: String, pageOrientation: PageOrientation, size: String, margin: Margin)(implicit ec: ExecutionContext): Future[File] =
    Future.successful(new File(filePath))
}

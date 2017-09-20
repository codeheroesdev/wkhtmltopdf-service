package io.codeheroes.htmltopdf.infrastructure

import java.io.File
import java.util.UUID

import io.codeheroes.htmltopdf.domain.{Margin, PDFService}
import io.github.cloudify.scala.spdf.{PageOrientation, Pdf, PdfConfig}

import scala.concurrent.{ExecutionContext, Future}
import scala.xml.XML

class WkHtmlToPDFService(wkHtmlToPdfPath: String) extends PDFService {

  override def generatePdf(page: String, pageOrientation: PageOrientation, size: String, margin: Margin)(implicit ec: ExecutionContext): Future[File] = {
    val pdf = Pdf(wkHtmlToPdfPath, new PdfConfig {
      orientation := pageOrientation
      pageSize := size
      marginTop := margin.top
      marginBottom := margin.bottom
      marginLeft := margin.left
      marginRight := margin.right
    })
    val outputStream = new File(getFileName)
    Future {
      pdf.run(XML.loadString(page), outputStream)
    }.map(_ => outputStream)
  }

  private def getFileName = UUID.randomUUID().toString

}

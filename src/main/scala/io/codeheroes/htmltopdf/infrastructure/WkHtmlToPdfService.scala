package io.codeheroes.htmltopdf.infrastructure

import java.io.{ByteArrayOutputStream, File, OutputStream}

import io.codeheroes.htmltopdf.domain.{Margin, PDFService}
import io.github.cloudify.scala.spdf.{PageOrientation, Pdf, PdfConfig}

import scala.concurrent.{ExecutionContext, Future}

class WkHtmlToPdfService(implicit ec: ExecutionContext) extends PDFService {
  override def generatePdf(page: String, pageOrientation: PageOrientation, size: String, margin: Margin): Future[File] = {
    val pdf = Pdf(new PdfConfig {
      orientation := pageOrientation
      pageSize := size
      marginTop := margin.top
      marginBottom := margin.bottom
      marginLeft := margin.left
      marginRight := margin.right
    })
    val outputStream = new File("pdf.pdf")
    Future {
      pdf.run(page, outputStream)
    }.map(_ => outputStream)
  }
}

package io.codeheroes.htmltopdf.api

import java.io.OutputStream

import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.ContentNegotiator.Alternative.ContentType
import akka.stream.scaladsl.StreamConverters
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import io.codeheroes.htmltopdf.api.core.DomainFormatters
import io.codeheroes.htmltopdf.domain.PDFService


class PdfRoutes(pdfService: PDFService) extends Json4sSupport {
  private implicit val formats = DefaultFormats ++ DomainFormatters.all
  private implicit val serialization = Serialization

  val routes = (path("generate") & post & entity(as[GeneratePDFRequest])) { request =>
    onSuccess(pdfService.generatePdf(request.content, request.orientation, request.pageSize, request.margin)) { file =>
      getFromFile(file)
    }
  }

  def byteSource(outputStream: OutputStream) = {
    StreamConverters
      .fromOutputStream(() => outputStream)
  }
}

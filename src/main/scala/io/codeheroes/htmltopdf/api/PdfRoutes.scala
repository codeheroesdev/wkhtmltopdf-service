package io.codeheroes.htmltopdf.api

import akka.http.scaladsl.model._
import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.FileIO
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import io.codeheroes.htmltopdf.api.core.DomainFormatters
import io.codeheroes.htmltopdf.domain.PDFService

import scala.concurrent.ExecutionContext

class PdfRoutes(pdfService: PDFService)(implicit ec: ExecutionContext) extends Json4sSupport {
  private implicit val formats = DefaultFormats ++ DomainFormatters.all
  private implicit val serialization = Serialization

  val routes = (path("generate") & post & entity(as[GeneratePDFRequest])) { request =>
    onSuccess(pdfService.generatePdf(request.content, request.orientation, request.pageSize, request.margin)) { file =>
      val byteStringSource = FileIO.fromPath(file.toPath).watchTermination() {
        case (res, _) => res.onComplete {
          case _ => file.delete()
        }
      }
      complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/octet-stream`, byteStringSource)))
    }
  }
}

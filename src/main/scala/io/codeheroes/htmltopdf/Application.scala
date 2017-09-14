package io.codeheroes.htmltopdf

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import com.typesafe.scalalogging.StrictLogging
import io.codeheroes.htmltopdf.api.PdfRoutes
import io.codeheroes.htmltopdf.api.core.EndpointsWrapper
import io.codeheroes.htmltopdf.infrastructure.WkHtmlToPdfService

import scala.util.{Failure, Success}

final case class APIConfig(bindHost: String, bindPort: Int)

class Application(config: Config, apiConfig: APIConfig) extends StrictLogging {

  private implicit val system = ActorSystem("ActorSystem", config)
  private implicit val dispatcher = system.dispatcher
  private implicit val materializer = ActorMaterializer()
  private implicit val scheduler = system.scheduler

  private val pdfService = new WkHtmlToPdfService
  private val pdfRoutes = new PdfRoutes(pdfService)
  private val routing = new EndpointsWrapper(pdfRoutes.routes).routing

  def start(): Unit = {
    Http().bindAndHandle(routing, apiConfig.bindHost, apiConfig.bindPort).onComplete {
      case Success(_) => logger.info(s"API started at ${apiConfig.bindHost}:${apiConfig.bindPort}")
      case Failure(ex) => logger.error(s"Cannot bind API to ${apiConfig.bindHost}:${apiConfig.bindPort}", ex)
    }
  }
}

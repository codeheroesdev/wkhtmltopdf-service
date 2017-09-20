package io.codeheroes.htmltopdf.api.core

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import akka.http.scaladsl.settings.RoutingSettings
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

import scala.util.control.NonFatal

class EndpointsWrapper(endpoints: server.Route*)(implicit system: ActorSystem) extends Json4sSupport with StrictLogging  {
  private val routingSettings = RoutingSettings(system.settings.config)
  private val rejectionHandler = RejectionHandler.default
  private implicit val formats = DefaultFormats
  private implicit val serialization = Serialization

  private val exceptionHandler = ExceptionHandler {
    case NonFatal(e) ⇒ ctx ⇒ {
      ctx.log.error(e, "Error during processing of request: '{}'. Completing with {} response.", ctx.request, InternalServerError)
      ctx.complete(InternalServerError)
    }
  }

  def routing: server.Route = {
    handleRejections(rejectionHandler) {
      handleExceptions(exceptionHandler.seal(routingSettings)) {
        endpoints.reduce(_ ~ _)
      }
    }
  }
}

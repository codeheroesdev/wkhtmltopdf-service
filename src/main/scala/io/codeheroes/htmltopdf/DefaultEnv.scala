package io.codeheroes.htmltopdf

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import io.codeheroes.htmltopdf.infrastructure.WkHtmlToPDFService

class DefaultEnv extends Env {
  private val config = ConfigFactory.load("default.conf")
  private val apiConfig = APIConfig(config.getString("application.api.bind-host"), config.getInt("application.api.bind-port"))
  private implicit val system = ActorSystem("StartSystem")
  private implicit val ec = system.dispatcher

  override def toApplication: Application = new Application(config, apiConfig, new WkHtmlToPDFService(config.getString("wkhtmltopdf.path")))

}

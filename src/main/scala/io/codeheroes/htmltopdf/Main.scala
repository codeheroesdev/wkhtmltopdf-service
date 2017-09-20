package io.codeheroes.htmltopdf

import com.typesafe.config.ConfigFactory
import io.codeheroes.htmltopdf.infrastructure.WkHtmlToPDFService

object Main extends App {
  private val config = ConfigFactory.load("default.conf")
  private val apiConfig = APIConfig(config.getString("application.api.bind-host"), config.getInt("application.api.bind-port"))

  private val app = new Application(config, apiConfig, new WkHtmlToPDFService(config.getString("wkhtmltopdf.path")))

  app.start()

}

package io.codeheroes.htmltopdf

import com.typesafe.config.ConfigFactory

class ProductionEnv extends Env {
  private val config = ConfigFactory.load("production.conf")
  private val apiConfig = APIConfig(config.getString("application.api.bind-host"), config.getInt("application.api.bind-port"))

  override def toApplication: Application = new Application(config, apiConfig)
}

package io.codeheroes.htmltopdf

trait Env {
  def toApplication: Application
}

object Main extends App {
  val environment = args.headOption match {
    case None => new DefaultEnv
    case Some("production") => new ProductionEnv
    case other => throw new UnsupportedOperationException(s"Cannot start application for config: $other.")
  }

  environment.toApplication.start()
}

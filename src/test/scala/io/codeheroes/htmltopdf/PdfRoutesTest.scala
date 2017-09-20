package io.codeheroes.htmltopdf

import akka.http.scaladsl.server
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Framing, Sink}
import akka.util.ByteString
import com.typesafe.config.ConfigFactory
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import io.codeheroes.htmltopdf.api.PdfRoutes
import io.codeheroes.htmltopdf.mock.PdfServiceMock
import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class PdfRoutesTest extends FlatSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll with Json4sSupport {

  private implicit val format = DefaultFormats
  private implicit val serialization = Serialization
  private implicit val timeout = RouteTestTimeout(30 seconds)
  implicit val mat = ActorMaterializer()

  protected var endpoint: server.Route = _

  override protected def beforeAll(): Unit = {
    val testFilePath = classOf[PdfRoutesTest].getClassLoader.getResource("test.txt").getPath
    val pdfService = new PdfServiceMock(testFilePath)
    val application = new Application(ConfigFactory.load("test.conf"), APIConfig("localhost", 8080), pdfService)
    endpoint = application.routing
  }

  "POST /generate" should "return status OK" in {
    val request = Map(
      "orientation" -> "landscape",
      "pageSize" -> "Letter",
      "margin" -> Map(
        "top" -> "1in",
        "bottom" -> "1in",
        "left" -> "1in",
        "right" -> "1in"
      ),
      "content" -> "<html></html>"
    )
    Post("/generate", request) ~> endpoint ~> check {
      status shouldBe OK
      val fileContent = response.entity.dataBytes.via(Framing.delimiter(ByteString.fromString("\n"), maximumFrameLength = 256))
        .map(_.decodeString("US-ASCII")).runWith(Sink.seq).map(_.toList)
      val result = Await.result(fileContent, 5 seconds)
      result should contain only "test test"
    }
  }
}

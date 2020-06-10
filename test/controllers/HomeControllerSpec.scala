package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.ResultExtractors
import play.api.test.Helpers._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{ RequestHeader, Result }
import org.junit.Test
import org.junit.Assert._

import scala.concurrent.Future
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest{
  val testInput =
    """
      |{
      |  "name": "Test",
      |  "width": 30,
      |  "height": 41,
      |  "shape": [
      |    {
      |        "rectangle": {
      |            "x": 3,
      |            "y": 5,
      |            "height": 43,
      |            "width": 3,
      |            "color": "Green"
      |        }
      |    },
      |     {
      |        "circle": {
      |            "x": 3,
      |            "y": 5,
      |            "radius": 3,
      |            "color": "Red"
      |        }
      |    },
      |    {
      |        "square": {
      |            "x": 3,
      |            "y": 5,
      |            "width": 3,
      |            "color": "Blue"
      |        }
      |    }
      |   
      |  ]
      |}
      |""".stripMargin


  @Test def t1(): Unit = {
    given ec as scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    val controller = new HomeController(stubControllerComponents())
    val request = FakeRequest(POST, "/").withHeaders(HOST -> "localhost:9000").withBody(Json.toJson(testInput))
    val home:Future[Result] = route(app, request).get

    assertEquals(status(home),OK)
    assertEquals(contentAsString(home),testInput)
  }
}

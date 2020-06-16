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
      |root stick figure 100 100 #aabb00
      |rectangle 29 18 30 2 Green
      |rectangle 10 25 2 40 Blue
      |rectangle 20 46 2 20 Black
      |rectangle 20 46 30 2 Red
      |rectangle 40 46 30 2 Red
      |circle 30 10 8 Red
      |"""


  @Test def t1(): Unit = {
    given ec as scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    val controller = new HomeController(stubControllerComponents())
    val request = FakeRequest(POST, "/").withHeaders(HOST -> "localhost:9000").withBody(testInput)
    val home:Future[Result] = route(app, request).get

    assertEquals(status(home),OK)
  }
}

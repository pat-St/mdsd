package controllers

import javax.inject._
import model.Root
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() : Action[JsValue] = Action.async(parse.json) {
    implicit request => {
      request.body.validate[Root] match {
        case JsSuccess(userDetails, _) => Future.successful(Ok(Json.toJson(userDetails)))
        case _ => Future.successful(BadRequest(request.body))
      }
    }
  }
}

package controllers

import javax.inject._
import model._
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

  type Change = Conversion[ShapeType, Shape | Null]

  given shape as  Change =
    shape => (shape.rectangle,shape.square,shape.circle) match {
    case (Some(r),_,_) => r
    case (_,Some(s),_) => s
    case (_,_,Some(c)) => c
    case _ => null
  }

  def (shapes: Seq[ShapeType]).flattenOption(using shape: Change): Seq[Shape | Null] = shapes.map{shape}

  def (r: Root).filter: List[Shape | Null] = r.shapes.flattenOption.toList

  def index(): Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] => {
      request.body.validate[Root] match {
        case JsSuccess(shapes, _) => Future.successful(Ok(views.html.shapes(shapes,shapes.filter)))
        case _ => Future.successful(BadRequest(request.body))
      }
    }
  }
}

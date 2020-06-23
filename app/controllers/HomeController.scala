package controllers

import javax.inject._
import model._
import model.{Shape, Root}
import service.ErrorModel
import service._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import service.ShapeTransformer

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)(using ec: ExecutionContext) extends BaseController:

  def (input: String).parse: ErrorModel | List[Shape] = InputParser.inputParse(input)

  def index(): Action[String] = 
      Action.async(parse.text) { 
            request => ShapeTransformer.apply(request.body.parse) match
                  case roots: List[Shape] => Future.successful(Ok(views.html.shapes(roots)))
                  case error: ErrorModel => Future.successful(BadRequest(views.html.error(error)))
      }
  

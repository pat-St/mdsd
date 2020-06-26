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

  type ShapeResult = ErrorModel | List[Shape]

  def (input: String).parse: ShapeResult = InputParser.inputParse(input)

  def (input: ShapeResult).transform: Either[List[Shape], List[ErrorModel]] = ShapeTransformer(input)
  
  def index(): Action[String] = 
      Action.async(parse.text) { 
            request => request.body.parse.transform match
                  case Left(roots) => Future.successful(Ok(views.html.shapes(roots)))
                  case Right(error) => Future.successful(BadRequest(views.html.error(error)))
      }
  

package controllers

import javax.inject._
import model._
import model.{Shape, Root}
import service.ParserErrorModel
import service._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)(using ec: ExecutionContext) extends BaseController:
  //Implicit Conversions
  type ShapeTransform = Conversion[Shape,ShapeColorChange[Shape]]
  
  //Extension methods
  def (color: String).cmatch: String = 
    Color.valueOf(color) match 
      case c: Color     => c.rgb
      case null         => color

  //Match types
  type ShapeColorChange[X <: Shape] <: Shape = X match {
        case Root       => Root
        case Rectangle  => Rectangle
        case Square     => Square
        case Circle     => Circle
  }

  //Given instances
  given validateColor as ShapeTransform:
      def apply(s: Shape) = s match 
            case s: Root      => s.copy(color=cmatch(s.color), shapes=s.shapes.validate)
            case s: Rectangle => s.copy(color=cmatch(s.color))
            case s: Square    => s.copy(color=cmatch(s.color))
            case s: Circle    => s.copy(color=cmatch(s.color))
  
  //Using clauses
  def (shapes: List[Shape]).validate(using vC: ShapeTransform) = shapes.map{s => vC.apply(s)}

  //Union type
  def (s: String).parse: List[Shape] | ParserErrorModel = InputParser.inputParse(s)

  def index(): Action[String] = 
      Action.async(parse.text) { 
            request => request.body.parse match 
                  case roots: List[Shape]       => Future.successful(Ok(views.html.shapes(roots.validate)))
                  case msg: ParserErrorModel    => Future.successful(BadRequest(views.html.error(msg)))
      }
  

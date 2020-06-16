package controllers

import javax.inject._
import model._
import model.Root
import service._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController:

  // type ShapeSubType = Rectangle | Square | Circle | Root | Shape

  // given shape as Transformer[ShapeType,Shape]:
  //   def (s : ShapeType).flatten = Seq(s.rectangle,s.circle,s.square)
  //   def transform(x: Seq[ShapeType]): Change = 
  //     x match 
  //       case Nil => Seq()
  //       case s::rest => s.flatten ++ transform(rest)
  
  // def (color: String).cmatch: String = 
  //   Color.valueOf(color) match 
  //     case c: Color => c.rgb
  //     case null => color

  // def (shape: ShapeSubType).validate: Shape = 
  //   shape match 
  //     case s: Root => s.copy(color=cmatch(s.color))
  //     case s: Rectangle => s.copy(color=cmatch(s.color))
  //     case s: Square => s.copy(color=cmatch(s.color))
  //     case s: Circle => s.copy(color=cmatch(s.color))
  //     case s: Shape => s

  // def [T1,T2](shapes: Seq[T1]).flattenOption(using shape: Transformer[T1,T2]): Seq[T2] = shape.transform(shapes).flatten

  def (s: String).parse = InputParser.inputParse(s)

  def index(): Action[String] = Action.async(parse.text) { request => request.body.parse match 
        case root: Root => Future.successful(Ok(views.html.shapes(root,root.shapes)))
        case msg: String => Future.successful(BadRequest(msg))
  }

package model

import model.Shape
import model.{Rectangle, Circle, Square}
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

case class ShapeType(rectangle: Option[Rectangle] = None, circle: Option[Circle] = None, square: Option[Square] = None)

// Pattern Matching
object IsRectangle:
  def unapply(s: ShapeType): Boolean = s.rectangle.nonEmpty

object ShapeType:
  val shapeTypeReaders: Reads[ShapeType] = (
      (JsPath \ "rectangle").readNullable[Rectangle] and
      (JsPath \ "circle").readNullable[Circle] and
      (JsPath \ "square").readNullable[Square]
    )(ShapeType.apply _)

  val shapeTypeWriters: OWrites[ShapeType] = (shape: ShapeType) => 
    shape match {
      case s @ IsRectangle() => Json.obj("rectangle" -> s.rectangle.get)
      case ShapeType(_,Some(v),_) => Json.obj("circle" -> v)
      case ShapeType(_,_,Some(v)) => Json.obj("square" -> v)
      case _ => Json.obj("none" -> "null")
    }

  given shapeTypeFormat as OFormat[ShapeType] = OFormat[ShapeType](shapeTypeReaders,shapeTypeWriters)

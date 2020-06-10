package model
import scala.collection.mutable.ListBuffer
import play.api.libs.json
import javax.inject.{Inject, Singleton}
import model.ShapeType.{shapeTypeReaders, shapeTypeWriters}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._

trait Shape 

case class Root(name: String,width: Int,height: Int,shapes : Seq[ShapeType]) extends Shape
case class ShapeType(
  rectangle: Option[Rectangle], 
  circle: Option[Circle], 
  square: Option[Square]
)

case class Rectangle( x: Int,  y: Int,  height: Int,  width: Int,  color: String) extends Shape
case class Circle( x: Int,  y: Int,  radius: Int,  color: String) extends Shape
case class Square( x: Int,  y: Int,  width: Int,  color: String) extends Shape

object Root {
  //given shapeTypeFormat as OFormat[ShapeType] = OFormat[ShapeType](shapeTypeReaders,shapeTypeWriters)
  val rootReads: Reads[Root] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "height").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "shape").read[Seq[ShapeType]]
    ) (Root.apply _)

  val rootWriters: OWrites[Root] = (root: Root) => {
    Json.obj("name" -> root.name,
      "width" -> root.width,
      "height" -> root.height,
      "shape" -> root.shapes
    )
  }
  given rootFormat as OFormat[Root] = OFormat[Root](rootReads,rootWriters)
}
object ShapeType {
  val shapeTypeReaders: Reads[ShapeType] = (
    (JsPath \ "rectangle").readNullable[Rectangle] and
      (JsPath \ "circle").readNullable[Circle] and
      (JsPath \ "square").readNullable[Square]
    )(ShapeType.apply _)

  val shapeTypeWriters: OWrites[ShapeType] = (shape: ShapeType) => {
    shape match {
      case ShapeType(Some(v),_,_) => Json.obj("rectangle" -> v)
      case ShapeType(_,Some(v),_) => Json.obj("circle" -> v)
      case ShapeType(_,_,Some(v)) => Json.obj("square" -> v)
      case _ => Json.obj("none" -> "null")
    }
  }
  given shapeTypeFormat as OFormat[ShapeType] = OFormat[ShapeType](shapeTypeReaders,shapeTypeWriters)
}
object Rectangle {
  val rectangleReads: Reads[Rectangle] = (
    (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "height").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Rectangle.apply _)

  val rectangleWrites: OWrites[Rectangle] = (rectangle: Rectangle) => {
    Json.obj("x" -> rectangle.x,
      "y" -> rectangle.y,
      "height" -> rectangle.height,
      "width" -> rectangle.width,
      "color" -> rectangle.color
    )
  }
  given rectangleFormat as OFormat[Rectangle] = OFormat[Rectangle](rectangleReads,rectangleWrites)
}
object Circle {
  val circleReads: Reads[Circle] = (
    (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "radius").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Circle.apply _)

  val circleWrites: OWrites[Circle] = (circle: Circle) => {
    Json.obj("x" -> circle.x,
      "y" -> circle.y,
      "radius" -> circle.radius,
      "color" -> circle.color
    )
  }
  given circleFormat as OFormat[Circle] = OFormat[Circle](circleReads,circleWrites)
}
object Square {
  val squareReads: Reads[Square] = (
    (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Square.apply _)

  val squareWrites: OWrites[Square] = (square: Square) => {
    Json.obj("x" -> square.x,
      "y" -> square.y,
      "width" -> square.width,
      "color" -> square.color
    )
  }
  given squareFormat as OFormat[Square] = OFormat[Square](squareReads,squareWrites)
}


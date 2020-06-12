package model

import model.Shape
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

case class Rectangle( x: Int,  y: Int,  height: Int,  width: Int,  color: String) extends Shape

object Rectangle {
  val rectangleReads: Reads[Rectangle] = (
      (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "height").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Rectangle.apply _)

  val rectangleWrites: OWrites[Rectangle] = (rectangle: Rectangle) => {
    Json.obj(
      "x" -> rectangle.x,
      "y" -> rectangle.y,
      "height" -> rectangle.height,
      "width" -> rectangle.width,
      "color" -> rectangle.color
    )
  }
  given rectangleFormat as OFormat[Rectangle] = OFormat[Rectangle](rectangleReads,rectangleWrites)
}
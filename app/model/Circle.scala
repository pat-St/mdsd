package model

import model.Shape
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

case class Circle( x: Int,  y: Int,  radius: Int,  color: String) extends Shape(color)

object Circle:
  val circleReads: Reads[Circle] = (
      (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "radius").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Circle.apply _)

  val circleWrites: OWrites[Circle] = (circle: Circle) => 
    Json.obj(
      "x" -> circle.x,
      "y" -> circle.y,
      "radius" -> circle.radius,
      "color" -> circle.color
    )

  given circleFormat as OFormat[Circle] = OFormat[Circle](circleReads,circleWrites)

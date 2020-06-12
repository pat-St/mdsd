package model

import model.Shape
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

case class Square(x: Int, y: Int, width: Int, color: String) extends Shape

object Square {
  val squareReads: Reads[Square] = (
      (JsPath \ "x").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "y").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "color").read[String]
    )(Square.apply _)

  val squareWrites: OWrites[Square] = (square: Square) => {
    Json.obj(
      "x" -> square.x,
      "y" -> square.y,
      "width" -> square.width,
      "color" -> square.color
    )
  }
  given squareFormat as OFormat[Square] = OFormat[Square](squareReads,squareWrites)
}

package model

import model.Shape
import model.ShapeType.{shapeTypeReaders, shapeTypeWriters}
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer
import javax.inject.{Inject, Singleton}

case class Root(name: String,width: Int,height: Int,shapes : Seq[ShapeType], color: String) extends Shape(color)

object Root:
  val rootReads: Reads[Root] = (
      (JsPath \ "name").read[String] and
      (JsPath \ "width").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "height").read[Int](min(1) keepAnd max(1000)) and
      (JsPath \ "shapes").read[Seq[ShapeType]] and
      (JsPath \ "color").read[String]
    ) (Root.apply _)

  val rootWriters: OWrites[Root] = (root: Root) => 
    Json.obj(
      "name" -> root.name,
      "width" -> root.width,
      "height" -> root.height,
      "shapes" -> root.shapes,
      "color" -> root.color
    )
  
  given rootFormat as OFormat[Root] = OFormat[Root](rootReads,rootWriters)
package model

import model.Shape

case class Root(name: String,width: Int,height: Int,shapes : List[Shape], color: String) extends Shape(color)
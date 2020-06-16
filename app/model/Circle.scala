package model

import model.Shape

case class Circle( x: Int,  y: Int,  radius: Int,  color: String) extends Shape(color)

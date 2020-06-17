package model

import model.Shape

case class Rectangle(x: Int, y: Int, height: Int, width: Int, color: String) extends Shape(color)

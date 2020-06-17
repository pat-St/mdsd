package model

import model.Shape
  
//Enum with parameters
enum Color(val rgb: String):
  case Red    extends Color("#FF0000")
  case Green  extends Color("#00FF00")
  case Blue   extends Color("#0000FF")
  case Black  extends Color("#000000")
  case White  extends Color("#FFFFFF")
  case Orange extends Color("#FFA500")
  case Yellow extends Color("#FFFF00")

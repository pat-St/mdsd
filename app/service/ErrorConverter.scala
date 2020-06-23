package service
import model._
import model.ErrorType

//Structural types
type ErrorModel = ErrorConverter.DisplayError {
      val header: ErrorType
      val body: String
} 

object ErrorConverter: 
  case class DisplayError(elems: (String, Any)*) extends Selectable:
    def selectDynamic(name: String): Any = elems.find(_._1 == name).get._2

  def convertToReturnError(s: String): ErrorModel = 
    DisplayError("header" -> ErrorType.ParserError, "body" -> s).asInstanceOf[ErrorModel]
  def converToFailureError(s: String): ErrorModel = 
    DisplayError("header" -> ErrorType.InputError, "body" -> s).asInstanceOf[ErrorModel]
  def convertToTransformError(s: String): ErrorModel =
    DisplayError("header" -> ErrorType.TransformError, "body" -> s).asInstanceOf[ErrorModel]

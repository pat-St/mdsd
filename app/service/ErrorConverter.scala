package service
import model._
import model.ErrorType

// structural type
type ParserErrorModel = ErrorConverter.DisplayError {
      val header: ErrorType
      val body: String
} 

object ErrorConverter: 
  case class DisplayError(elems: (String, Any)*) extends Selectable:
    def selectDynamic(name: String): Any = elems.find(_._1 == name).get._2

  def convertToReturnError(s: String): ParserErrorModel = 
    DisplayError("header" -> ErrorType.ParserError, "body" -> s).asInstanceOf[ParserErrorModel]
  def converToFailureError(s: String): ParserErrorModel = 
    DisplayError("header" -> ErrorType.InputError, "body" -> s).asInstanceOf[ParserErrorModel]

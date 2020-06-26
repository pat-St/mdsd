package service
import model._
import model.ErrorType

//Structural types
type ErrorModel = ErrorConverter.DisplayError {
      val header: ErrorType
      val pos: Option[Int]
      val msg: String
      val extra: String
} 

object ErrorConverter: 
  case class DisplayError(elems: (String, Any)*) extends Selectable:
    private val fields = elems.toMap
    def selectDynamic(name: String): Any = fields(name)

  def convertToReturnError(pos: Option[Int], msg: String, extra: String): ErrorModel = 
    DisplayError("header" -> ErrorType.ParserError, "pos" -> pos, "msg" -> msg, "extra" -> extra).asInstanceOf[ErrorModel]
  def converToFailureError(pos: Option[Int], msg: String, extra: String): ErrorModel = 
    DisplayError("header" -> ErrorType.InputError, "pos" -> pos, "msg" -> msg, "extra" -> extra).asInstanceOf[ErrorModel]
  def convertToTransformError(pos: Option[Int], msg: String = "", extra: String = ""): ErrorModel =
    DisplayError("header" -> ErrorType.TransformError, "pos" -> pos, "msg" -> msg, "extra" -> extra).asInstanceOf[ErrorModel]

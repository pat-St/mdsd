package service
import model._
import service._
import service.ShapeErrorUtil

private type ShapeColorChange[X <: Shape] <: Shape | ErrorModel = X match {
  case Root       => Root       | ErrorModel
  case Rectangle  => Rectangle  | ErrorModel
  case Square     => Square     | ErrorModel
  case Circle     => Circle     | ErrorModel
}

type ShapeColorValidator = Conversion[Shape,ShapeColorChange[Shape]]

given validateColor(using sEU: ShapeErrorUtil) as ShapeColorValidator:
  def apply(s: Shape) = s match 
    case shape: Root      => shape.isValid
    case shape: Rectangle => shape.isValid
    case shape: Square    => shape.isValid
    case shape: Circle    => shape.isValid

  def (shapes: List[Shape]).validate: List[Shape | ErrorModel] = shapes.map(s => validateColor(s))

  def (color: String).cmatch: String | ErrorModel = 
    Color.values.filter(_.toString == color).headOption match 
      case Some(c)   => c.rgb
      case None      => ErrorConverter.convertToTransformError(pos=None,msg="Is not a valid color",extra=color)

  def (r: Root).isValid: Root | ErrorModel = 
    r.color.cmatch match 
      case s: String        => r.copy(color=s).isAllValid
      case err: ErrorModel  => err

  def (r: Root).isAllValid: Root | ErrorModel =
    sEU(r.shapes.validate) match
      case Left(x)  => r.copy(shapes=x)
      case Right(x) => x.head

  def (r: Rectangle).isValid: Rectangle | ErrorModel = 
    r.color.cmatch match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err

  def (r: Square).isValid: Square | ErrorModel = 
    r.color.cmatch match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err

  def (r: Circle).isValid: Circle | ErrorModel = 
    r.color.cmatch match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err
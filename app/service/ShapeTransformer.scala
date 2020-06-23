package service
import scala.io.StdIn._
import model._

def (color: String).cmatch: String | ErrorModel = 
  Color.valueOf(color) match 
    case c: Color     => c.rgb
    case null         => ErrorConverter.convertToTransformError(color + "is not a valid color")

object ShapeTransformer:
  def apply(parseResult: ErrorModel | List[Shape]): ErrorModel | List[Shape] = parseResult match
      case roots: List[Shape] => {
        roots.foreach(shape => {
          if(checkColor(shape).isInstanceOf[ErrorModel]) {
            return checkColor(shape).asInstanceOf[ErrorModel]
          }
        })
        return roots
      }
      case parseerror: ErrorModel => parseerror

def checkColor(shape: Shape): ErrorModel | Shape = shape match
  case circle: Circle => Color.valueOf(circle.color) match
    case null => ErrorConverter.convertToTransformError(circle.color + " is not a valid color for a circle")
    case c: Color => shape
  case rect: Rectangle => Color.valueOf(rect.color) match
    case null => ErrorConverter.convertToTransformError(rect.color + " is not a valid color for a rectangle")
    case c: Color => shape
  case root: Root => Color.valueOf(root.color) match
    case null => ErrorConverter.convertToTransformError(root.color + " is not a valid color for a root")
    case c: Color => shape
  case square: Square => Color.valueOf(square.color) match
    case null => ErrorConverter.convertToTransformError(square.color + " is not a valid color for a square")
    case c: Color => shape

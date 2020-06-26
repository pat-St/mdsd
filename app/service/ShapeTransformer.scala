package service
import scala.io.StdIn._
import model._

object ShapeTransformer:

  type ShapeTransform = Conversion[Shape,ShapeColorChange[Shape]]

  type EitherShape = Either[List[Shape],List[ErrorModel]]

  type ShapeColorChange[X <: Shape] <: Shape | ErrorModel = X match {
    case Root       => Root       | ErrorModel
    case Rectangle  => Rectangle  | ErrorModel
    case Square     => Square     | ErrorModel
    case Circle     => Circle     | ErrorModel
  }

  def apply(parseResult: ErrorModel | List[Shape]): EitherShape = 
    parseResult match 
      case shape: List[Shape] => shape.validate
      case err: ErrorModel    => err
      match 
      case shape: List[Shape | ErrorModel]  => shape.splitError.filterError        
      case error: ErrorModel                => Right(List(error))

  def (shapes: List[Shape]).validate(using vC: ShapeTransform): List[Shape | ErrorModel] = shapes.map{s => vC(s)}

  given validateColor as ShapeTransform:
    def apply(s: Shape) = s match 
          case shape: Root      => shape.isValid
          case shape: Rectangle => shape.isValid
          case shape: Square    => shape.isValid
          case shape: Circle    => shape.isValid

  def (color: String).cmatch: String | ErrorModel = 
    Color.values.filter(c => c.toString == color).headOption match 
      case None      => ErrorConverter.convertToTransformError(pos=None,msg="Is not a valid color",extra=color)
      case Some(c)   => c.rgb

  def (r: Root).isValid: Root | ErrorModel = 
    cmatch(r.color) match 
      case s: String        => r.copy(color=s).isAllValid
      case err: ErrorModel  => err

  def (r: Root).isAllValid: Root | ErrorModel =
    r.shapes.validate.splitError.filterError match
      case Left(x)  => r.copy(shapes=x)
      case Right(x) => x.head

  def (r: Rectangle).isValid: Rectangle | ErrorModel = 
    cmatch(r.color) match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err

  def (r: Square).isValid: Square | ErrorModel = 
    cmatch(r.color) match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err

  def (r: Circle).isValid: Circle | ErrorModel = 
    cmatch(r.color) match 
      case s: String        => r.copy(color=s)
      case err: ErrorModel  => err
  
  def (shape: List[Shape | ErrorModel]).splitError: (List[Shape], List[ErrorModel]) = 
    shape match 
      case Nil          => (Nil,Nil) 
      case head :: rest => head match
        case s: Shape       => (s :: rest.splitError._1 , rest.splitError._2    )
        case s: ErrorModel  => (rest.splitError._1      , s :: rest.splitError._2)
      
  def (shape: (List[Shape],List[ErrorModel])).filterError: EitherShape = 
    shape match 
      case (s, Nil) => Left(s)
      case (_, s  ) => Right(s)
    

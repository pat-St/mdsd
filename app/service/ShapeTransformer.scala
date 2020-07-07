package service
//import scala.io.StdIn._
import model._

type EitherShape = Either[List[Shape],List[ErrorModel]]

object ShapeTransformer:
  def apply(parseResult: ErrorModel | List[Shape]): EitherShape = 
    parseResult match 
      case shape: List[Shape]               => shape.validateColor
      case err: ErrorModel                  => err
    match 
      case shape: List[Shape | ErrorModel]  => sEu(shape)        
      case error: ErrorModel                => Right(error)
    match
      case Left(shape)                      => shape.validateDim
      case Right(err)                       => err
    match 
      case shape: List[Shape | ErrorModel]  => sEu(shape)        
      case error: ErrorModel                => Right(List(error))
    
  def (shapes: List[Shape]).validateColor
    (using validateColor: ShapeColorValidator)
    (using seu: ShapeErrorUtil)
    : List[Shape | ErrorModel] = shapes.map(s => validateColor(s))

  def (shapes: List[Shape]).validateDim
    (using validateDim: ShapeDimensionValidator)
    (using seu: ShapeErrorUtil)
    : List[Shape | ErrorModel] = shapes.map(s => validateDim((s,0,0)))

package service
import model._
import service._

type ShapeErrorUtil = Conversion[List[Shape | ErrorModel],EitherShape]

given sEu as ShapeErrorUtil:
  def apply(shape: List[Shape | ErrorModel]): EitherShape = shape.splitError.filterError
  def (shape: List[Shape | ErrorModel]).splitError: (List[Shape], List[ErrorModel]) = 
    shape match 
      case Nil              => (Nil,Nil) 
      case head :: leftover => head match
        case s: Shape      => (s :: leftover.splitError._1, leftover.splitError._2     )
        case s: ErrorModel => (leftover.splitError._1     , s :: leftover.splitError._2)
      
  def (shape: (List[Shape],List[ErrorModel])).filterError: EitherShape = 
    shape match 
      case (s, Nil) => Left(s)
      case (_, s  ) => Right(s)
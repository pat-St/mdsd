package service

import service._
import model._

private type ShapeDimensionCheck[X <: Shape] <: Shape | ErrorModel = X match {
  case Root       => Root       | ErrorModel
  case Rectangle  => Rectangle  | ErrorModel
  case Square     => Square     | ErrorModel
  case Circle     => Circle     | ErrorModel
}

type ShapeDimensionValidator = Conversion[(Shape,Int,Int),ShapeDimensionCheck[Shape]]

given validateDim(using sEU: ShapeErrorUtil) as ShapeDimensionValidator:

  type SelfDim    = ( Int     ,Int       )
  type ParentDim  = ( Int     ,Int       )
  type TupleInput = ( SelfDim ,ParentDim )

  object IsPositive {
    def unapply(input: (Int,Int)) = input._1 * input._2 > 0
  }

  def apply(input: (Shape, Int, Int)) = input._1 match 
    case shape: Root      => shape.isValid
    case shape: Rectangle => shape.isValid(input._2,input._3)
    case shape: Square    => shape.isValid(input._2,input._3)
    case shape: Circle    => shape.isValid(input._2,input._3)
  
  def (shapes: List[Shape]).validate(parentX: Int = 0, parentY: Int = 0): List[Shape | ErrorModel] = 
    shapes.map(s => validateDim((s,parentX,parentY)))

  def (input: TupleInput).dMatch: Boolean | ErrorModel = 
    (input._2._1-input._1._1, input._2._2-input._1._2)  match 
      case i @ IsPositive()   => true
      case _ => ErrorConverter.convertToTransformError(
          pos=None,
          msg="Is larger than Parent",
          extra=s"Current shape: x = ${input._1._1}, y = ${input._1._2} and parent x = ${input._2._1}, y = ${input._2._2}"
        )

  def (r: Root).isValid: Root | ErrorModel = r.isAllValid

  def (r: Root).isAllValid: Root | ErrorModel =
    sEU(r.shapes.validate(r.width,r.height)) match
      case Left(x)  => r.copy(shapes=x)
      case Right(x) => x.head

  def (r: Rectangle).isValid(parentX: Int, parentY: Int): Rectangle | ErrorModel = 
    ((r.x,r.y),(parentX,parentY)).dMatch match 
      case s: Boolean        => r
      case err: ErrorModel  => err

  def (r: Square).isValid(parentX: Int, parentY: Int): Square | ErrorModel = 
    ((r.x,r.y),(parentX,parentY)).dMatch match 
      case s: Boolean        => r
      case err: ErrorModel  => err

  def (r: Circle).isValid(parentX: Int, parentY: Int): Circle | ErrorModel = 
    ((r.x,r.y),(parentX,parentY)).dMatch match 
      case s: Boolean        => r
      case err: ErrorModel  => err
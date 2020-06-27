package service

import scala.util.parsing.combinator._
import model._
import service.{ErrorModel,ErrorConverter}

object InputParser extends Parser:
  def inputParse(input: String): List[Shape] | ErrorModel = 
    parseAll(rootsParser, input) match 
      case Success(matched, _)  => matched
      case Failure(msg, next)   => 
        val pos = next.pos
        ErrorConverter.converToFailureError(Some(pos.line),msg,pos.longString)
      case Error(msg, next)     => 
        val pos = next.pos
        ErrorConverter.convertToReturnError(Some(pos.line),msg,pos.longString)

class Parser extends RegexParsers:
  //basic type
  private def text:     Parser[String]  = """[^\d]+""".r        ^^  ( _.toString)
  private def color:    Parser[String]  = """[^\v\s\d]+""".r    ^^  ( _.toString)
  private def integer:  Parser[Int]     = """(0|[1-9]\d*)""".r  ^^  ( _.toInt   )

  //shape parameters
  private def x:          Parser[Int]     = integer
  private def y:          Parser[Int]     = integer 
  private def height:     Parser[Int]     = integer 
  private def width:      Parser[Int]     = integer 
  private def radius:     Parser[Int]     = integer 
  private def shapeTitle: Parser[String]  = text   
 
  //shape creator
  private def rectangle: Parser[Rectangle] =
    x ~ y ~ height ~ width ~ color      ^^ { case x ~ y  ~ h ~ w ~ c => Rectangle(x,y,h,w,c)  }
  private def square: Parser[Square] =
    x ~ y ~ width ~ color               ^^ { case x ~ y ~ w ~ c => Square(x,y,w,c)            }
  private def circle: Parser[Circle] =
    x ~ y ~ radius ~ color              ^^ { case x ~ y ~ r ~ c => Circle(x,y,r,c)            }
  private def root: Parser[Root] = 
    shapeTitle ~ width ~ height ~ color ^^ { case n ~ w ~ h ~ c => Root(n, w, h, List(),c)    }

  //shape identifier
  private def rectangleIdentifier:  Parser[Rectangle] =  "rectangle" ~> rectangle
  private def squareIdentifier:     Parser[Square]    =  "square"    ~> square   
  private def circleIdentifier:     Parser[Circle]    =  "circle"    ~> circle   
  private def rootIdentifier:       Parser[Root]      =  "root"      ~> root

  //shape matcher
  private def shape: Parser[Shape] = rectangleIdentifier | squareIdentifier | circleIdentifier

  private def shapeStructureIdentifier: Parser[Root] =
    rootIdentifier ~ rep1(shape) ^^ { case r ~ s => r.copy(shapes=s)}

  def rootsParser: Parser[List[Shape]] = rep1(shapeStructureIdentifier)


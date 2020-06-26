package service

import scala.util.parsing.combinator._
import model._
import service.ErrorModel
import service.ErrorConverter

object InputParser extends Parser:
  //Union type as return
  def inputParse(input: String): List[Shape] | ErrorModel = parseAll(rootsParser, input) match 
    case Success(matched, _)  => matched
    case Failure(msg, next)   => {
      val pos = next.pos
      ErrorConverter.converToFailureError(Some(pos.line),msg,pos.longString)
    }
    case Error(msg, next)     => {
      val pos = next.pos
      ErrorConverter.convertToReturnError(Some(pos.line),msg,pos.longString)
    }

class Parser extends RegexParsers:
  private def text:     Parser[String]  = """[^\d]+""".r  ^^ { _.toString }
  private def color:    Parser[String]  = """[^\v\s\d]+""".r  ^^ { _.toString }
  private def integer:  Parser[Int]     = """(0|[1-9]\d*)""".r ^^ { _.toInt }

  private def rectangle: Parser[Rectangle] =
    integer ~ integer ~ integer ~ integer ~ color ^^ { case x ~ y  ~ h ~ w ~ c => Rectangle(x,y,h,w,c) }
  private def square: Parser[Square] =
    integer ~ integer ~ integer ~ color ^^ { case x ~ y ~ w ~ c => Square(x,y,w,c) }
  private def circle: Parser[Circle] =
    integer ~ integer ~ integer ~ color ^^ { case x ~ y ~ r ~ c => Circle(x,y,r,c) }
  
  private def shape: Parser[Shape] =
    "rectangle" ~ rectangle ^^ { case _ ~ r => r } |
    "square"    ~ square    ^^ { case _ ~ s => s } |
    "circle"    ~ circle    ^^ { case _ ~ c => c }
  
  private def root: Parser[Root] = 
    "root" ~ text ~ integer ~ integer ~ color ~ rep1(shape) ^^ { 
      case _ ~ n ~ i1 ~ i2 ~ c ~ s => Root(n, i1, i2, s, c) 
    }

  def rootsParser: Parser[List[Shape]] = rep1(root) ^^ { case r => r}


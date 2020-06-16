package service

import scala.util.parsing.combinator._
import model._

object InputParser extends Parser:
  def inputParse(input: String): Root | String = parse(rootParser, input) match 
    case Success(matched, _) => matched
    case Failure(msg, _) => msg
    case Error(msg, _) =>  msg

class Parser extends RegexParsers:
  private def text: Parser[String] = """[^\d]+""".r  ^^ { _.toString }
  private def color: Parser[String] = """[^\v]+""".r  ^^ { _.toString }
  private def integer: Parser[Int] = """(0|[1-9]\d*)""".r ^^ { _.toInt }
  private def double: Parser[Double] = """\d+(\.\d*)?[^\s]+""".r ^^ (_.toDouble)

  private def rectangle: Parser[Rectangle] =
    integer ~ integer ~ integer ~ integer ~ color ^^ { case x ~ y  ~ h ~ w ~ c => Rectangle(x,y,h,w,c) }
  private def square: Parser[Square] =
    integer ~ integer ~ integer ~ color ^^ { case x ~ y ~ w ~ c => Square(x,y,w,c) }
  private def circle: Parser[Circle] =
    integer ~ integer ~ integer ~ color ^^ { case x ~ y ~ r ~ c => Circle(x,y,r,c) }
  private def root: Parser[Root] = 
      text ~ integer ~ integer ~ color ~ rep1(shape) ^^ { case n ~ i1 ~ i2 ~ c ~ s => Root(name=n,width=i1,height=i2,shapes=s,color=c) }
  
  private def shape: Parser[Shape] =
      "rectangle" ~ rectangle ^^ { case _ ~ r => r } |
      "square"    ~ square    ^^ { case _ ~ s => s } |
      "circle"    ~ circle    ^^ { case _ ~ c => c }

  def rootParser: Parser[Root] =
    "root" ~ root ^^ { case _ ~ r => r }


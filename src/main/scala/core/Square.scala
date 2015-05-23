package core

import me.mtrupkin.console.{Colors, ScreenChar}
import me.mtrupkin.core.{Point, Size}

import scala.Array._

/**
 * Created by mtrupkin on 12/14/2014.
 */
trait Square {
  def name: String
  def sc: ScreenChar
  def move: Boolean = true
  var cost: Int = 1
}

trait GameMap {
  def size: Size
  val squares = ofDim[Square](size.width, size.height)

  def apply(p: Point): Square = if (size.in(p)) squares(p.x)(p.y) else Bounds
  def update(p: Point, value: Square): Unit = squares(p.x)(p.y) = value
}

object Bounds extends Square {
  val name = "Out Of Bounds"
  override val move = false
  def sc = ScreenChar('\u25A0', fg = Colors.White)
}


class Space extends Square {
  val name = "Space"
  val sc = ScreenChar('\u00B7', fg = Colors.White)
}

class Star(val sc: ScreenChar) extends Square {
  val name = "Star"
}

object Square {
  implicit def toTile(s: ScreenChar): Square = {
    s.c match {
      case ' ' => new Space
      case _ => new Star(s)
    }
  }
}

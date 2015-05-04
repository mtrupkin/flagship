package model

import me.mtrupkin.console.Colors._
import me.mtrupkin.console.{RGB, Colors, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Matrix, Point, Size}

import scala.Array._
import scala.collection.mutable.HashMap
import scala.util.Random

/**
 * Created by mtrupkin on 12/14/2014.
 */
trait Square {
  def name: String
  def sc: ScreenChar
  def move: Boolean
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
  val move = false
  cost = Int.MaxValue
  def sc = ScreenChar('\u25A0', fg = Colors.White)
}

object Floor {
  def char = '\u00B7'
}

class Floor extends Square {
  val name = "Floor"
  val move = true
  cost = 100
  def color  = 240 * (100 - cost)/100 + 15
  def sc = ScreenChar(Floor.char, fg = RGB(color, color, color))
}

class Wall(val sc: ScreenChar) extends Square {
  val name = "Wall"
  val move = false
}



object Square {
  implicit def toTile(s: ScreenChar): Square = {
    s.c match {
      case ' ' | '.' | 'E' => new Floor
      case _ => new Wall(s)
    }
  }
  def floor(): Square = new Floor
  val unexplored = new Wall(' ')
  def wall(sc: ScreenChar) = new Wall(sc)
}

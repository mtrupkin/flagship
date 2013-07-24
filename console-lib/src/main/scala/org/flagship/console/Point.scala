package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
trait Position {
  def x: Int
  def y: Int
}

case class Point(x: Int, y: Int) extends Position

object Point {
  val ZERO: Point = new Point(0, 0)
 }

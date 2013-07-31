package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */

case class Point(x: Int, y: Int) {
  def move(p: Point): Point = {
    Point(x + p.x, y + p.y)
  }
}

object Point {
  val ZERO: Point = new Point(0, 0)
 }

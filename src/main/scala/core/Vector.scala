package core

import me.mtrupkin.core.Point

/**
 * Created by mtrupkin on 4/25/2015.
 */
case class Vector(x: Double, y: Double) {
  def +(p: Vector): Vector = Vector(x + p.x, y + p.y)
  def +=(p: Vector): Vector = Vector(x + p.x, y + p.y)
  def -(p: Vector): Vector = Vector(x - p.x, y - p.y)
  def *(u: Double): Vector = Vector(x*u, y*u)
  def /(u: Double): Vector = Vector(x/u, y/u)

  def magnitude: Double = Math.sqrt(x*x + y*y)

  def unit: Vector = /(magnitude)

  def normal(n: Double) = unit*n
}

object Vector {
  implicit def toPoint(v: Vector): Point = Point((v.x).toInt, (v.y).toInt)
  implicit def toVector(p: Point): Vector = Vector(p.x+0.5, p.y+0.5)
}

package model

import org.flagship.console.Point

/**
 * User: mtrupkin
 * Date: 12/18/13
 */
trait Entity {
  def position: Point
}

class Player(var position: Point = Point.ZERO) extends Entity {
  def up() = {position = position.move(Point.Up)}
  def down() = {position = position.move(Point.Down)}
  def left() = {position = position.move(Point.Left)}
  def right() = {position = position.move(Point.Right)}
}

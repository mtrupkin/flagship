package model

import org.flagship.console.Point

/**
 * User: mtrupkin
 * Date: 12/18/13
 */
trait Entity {
  def name: String
  def position: Point
}

class BaseEntity(val name: String, var position: Point = Point.Origin) extends Entity

object Entity {
  def None: Entity = new BaseEntity("None")
}

class Player() extends BaseEntity("Player") {

  def up() = {position = position.move(Point.Up)}
  def down() = {position = position.move(Point.Down)}
  def left() = {position = position.move(Point.Left)}
  def right() = {position = position.move(Point.Right)}
}

package model

import core.{Vector}
import me.mtrupkin.core.{Size, Point}

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def position: Point
  def typeName: String
  def id: String
  def name: String
}

trait EntityBuilder {
  def typeName: String
  var count = 0

  def nextID(): String = {
    count += 1
    s"$typeName-$count"
  }
}

trait Body extends Entity {
  def p: Vector
  def position: Point = p
}

trait EntitySystem extends Entity {
  def entities: Seq[Entity]
}

trait DiscreteSystem extends EntitySystem {
  def size: Size
}

trait BodySystem extends EntitySystem {
  def radius: Int
}



package model

import core.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def name: String
  def id: String
  def position: Vector

  var parent: Entity = _
  def update(elapsed: Int) = {}
}

trait EntitySystem extends Entity {
  def children: Seq[Entity]
  override def update(elapsed: Int) = children.foreach { _.update(elapsed) }
}

object Entity {
  def typeName(e: Entity): String = e match {
    case _: Planet => Planet.typeName
    case _: Star => Star.typeName
    case _: StarSystem => StarSystem.typeName
    case _: Sector => Sector.typeName
  }
}



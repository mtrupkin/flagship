package model.space

import core.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */

trait Entity {
  def name: String
  def id: String
  def position: Vector

  def update(elapsed: Int): Unit = {}
}

object Entity {
  def typeName(e: Entity): String = e match {
    case _: Planet => Planet.typeName
    case _: Star => Star.typeName
    case _: StarSystem => StarSystem.typeName
    case _: Sector => Sector.typeName
  }
}



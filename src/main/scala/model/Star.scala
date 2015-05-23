package model

import core.Vector

/**
 * Created by mtrupkin on 5/12/2015.
 */
class Star(val id: String, val name: String, val p: Vector) extends Body {
  val typeName = Star.typeName
}

object Star extends EntityBuilder {
  val typeName = "Star"
  def apply(): Star = new Star(nextID(), "Alpha", Vector(0, 0))
}

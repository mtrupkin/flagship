package model.space

import model.Vector

/**
 * Created by mtrupkin on 5/12/2015.
 */
class Star(val name: String, val position: Vector) extends BodyEntity {
  def update(elapsed: Int): Unit = {}
}

object Star {
  def apply(): Star = { new Star("Alpha", Vector(0, 0)) }
}

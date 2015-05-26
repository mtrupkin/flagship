package model

/**
 * Created by mtrupkin on 5/12/2015.
 */
class StarSystem(val id: String, val name: String, val position: core.Vector, val star: Star, val planets: Seq[Planet]) extends EntitySystem {
  def entities = Seq(star) ++ planets
  def update(elapsed: Int): Unit = {}
}

object StarSystem {
  val typeName = "Star System"
  val typeID = "SS"
}

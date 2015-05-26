package model

/**
 * Created by mtrupkin on 5/12/2015.
 */
class StarSystem(val id: String, val name: String, val position: core.Vector, val star: Star, val planets: Seq[Planet]) extends EntitySystem {
  def children = Seq(star) ++ planets
}

object StarSystem {
  val typeName = "Star System"
  val typeID = "SS"
}

class Star(
  val id: String,
  val name: String,
  val position: core.Vector,
  val spectralType: Char) extends Entity

object Star {
  val typeName = "Star"
  val typeID = "ST"
}

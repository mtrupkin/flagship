package model

/**
 * Created by mtrupkin on 5/23/2015.
 */
class Planet(val parent: Entity, val id: String, val name: String, val position: core.Vector) extends Entity {
  def update(elapsed: Int): Unit = {}
}

class PlanetBuilder extends EntityBuilder {
  def apply(parent: StarSystem): Planet = {
    val id = s"${Planet.typeID}-${nextID()}"
    new Planet(parent, id, rndName(), Helper.vec())
  }
}

object Planet  {
  val typeName = "Planet"
  val typeID = "PL"
}
package model

/**
 * Created by mtrupkin on 5/23/2015.
 */
class Planet(val id: String, val name: String, val p: core.Vector) extends Body {
  def typeName: String = Planet.typeName
  def update(elapsed: Int): Unit = {}
}

object Planet extends EntityBuilder {
  val typeName = "Planet"
  def apply(): Planet = { new Planet(nextID(), "Alpha", Helper.vec()) }
}
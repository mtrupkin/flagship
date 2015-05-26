package model

/**
 * Created by mtrupkin on 5/23/2015.
 */
class Planet(val id: String, val name: String, val position: core.Vector) extends Entity {
}

object Planet  {
  val typeName = "Planet"
  val typeID = "PL"
}
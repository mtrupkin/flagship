package model


/**
 * Created by mtrupkin on 5/12/2015.
 */

class Star(val id: String, val name: String, val position: core.Vector, val spectralType: Char) extends Entity

object Star {
  val typeName = "Star"
  val typeID = "ST"
}

package model

import model.Star._

import scala.util.Random


/**
 * Created by mtrupkin on 5/12/2015.
 */
class Star(val parent: StarSystem, val id: String, val name: String, val position: core.Vector, val starClass: Char) extends Entity

class StarBuilder(val id: Int) extends EntityBuilder {
  import Star.typeID

  def rndStarClass(): Char = {
    val rnd = Random.nextInt(7)
    rnd match {
      case 0 => 'O'
      case 1 => 'B'
      case 2 => 'A'
      case 3 => 'F'
      case 4 => 'G'
      case 5 => 'K'
      case 6 => 'M'
    }
  }

  def apply(starSystem: StarSystem): Star = {
    new Star(starSystem, s"$typeID-$id", rndName(), core.Vector(0, 0), rndStarClass())
  }
}

object Star {
  val typeName = "Star"
  val typeID = "ST"
}

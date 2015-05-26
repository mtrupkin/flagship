package model

import me.mtrupkin.core.Point
import model.StarSystem._

import scala.util.Random

/**
 * Created by mtrupkin on 5/12/2015.
 */
class StarSystem(val parent: Sector, val id: String, val name: String, val position: core.Vector) extends EntitySystem {
  var star: Star = _
  var planets: Seq[Planet] = _

  def setChildren(star: Star, planets: Seq[Planet]) = {
    this.star = star
    this.planets = planets
  }

  def entities = Seq(star) ++ planets
  def update(elapsed: Int): Unit = {}
}

class StarSystemBuilder extends EntityBuilder {

  def apply(sector: Sector): StarSystem = {
    import StarSystem.typeID
    val id = nextID()
    val idName = s"$typeID-$id"

    val starSystem = new StarSystem(sector, idName, rndName(), Helper.pos())

    val planetBuilder = new PlanetBuilder
    val starBuilder = new StarBuilder(id)

    val planetCount = 2 + Random.nextInt(5)
    val planets = for (i <- 0 to planetCount) yield planetBuilder.apply(starSystem)


    val star = starBuilder.apply(starSystem)
    starSystem.setChildren(star, planets)
    starSystem
  }
}

object StarSystem {
  val typeName = "Star System"
  val typeID = "SS"
}

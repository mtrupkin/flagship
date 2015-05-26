package model

import me.mtrupkin.core.{Point, Points, Size}
import model.Sector._

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val id: String, val name: String, val position: core.Vector) extends EntitySystem {
  val parent: Entity = this
  var starSystems: Seq[StarSystem] = _

  def update(elapsed: Int): Unit = {}
  def entities = starSystems
}

object Helper {
  def pos(): core.Vector = core.Vector(Random.nextDouble()*40, Random.nextDouble()*20)
  def vec(): core.Vector = core.Vector(Random.nextDouble()*100-50, Random.nextDouble()*100-50)
}

class SectorBuilder extends EntityBuilder {
  def apply(): Sector = {
    import Sector.typeID

    val sector = new Sector(s"$typeID-${nextID()}", "Alpha", Points.Origin)

    val starSystemBuilder = new StarSystemBuilder
    val starSystemCount = 30 + Random.nextInt(20)
    val starSystems = for (i <- 0 to starSystemCount) yield starSystemBuilder.apply(sector)

    sector.starSystems = starSystems
    sector
  }
}

object Sector  {
  val typeName = "Sector"
  val typeID = "SE"
}



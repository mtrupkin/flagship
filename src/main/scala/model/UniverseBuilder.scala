package model

import me.mtrupkin.core.Points
import model.space._

import scala.util.Random

/**
 * Created by mtrupkin on 5/26/2015.
 */
import Entity._
import core.Vector

class EntityBuilder(val typeID: String) {
  private var count = 0

  def currentID(): Int = count

  def nextID(): String = {
    count += 1
    s"$typeID-$count"
  }

  def rndName(): String = {
    val name = for {
      i <- 0 to 5
      c = Random.nextPrintableChar()
      if c.isLetter
    } yield c
    name.mkString
  }

  def rndPosition(): Vector = Vector(Random.nextDouble()*40, Random.nextDouble()*20)
  def rndVector(): Vector = Vector(Random.nextDouble()*100-50, Random.nextDouble()*100-50)
}

object UniverseBuilder {
  def apply(): Universe = {
    val sector = SectorBuilder(UniverseID)

    val player = ShipBuilder(sector.starSystems(0).id)
    new Universe(Seq(sector), Seq(player))
  }
}

object SectorBuilder extends EntityBuilder(SectorID) {
  def apply(parentID: String): Sector = {
    val id = nextID()
    val starSystemCount = 30 + Random.nextInt(20)
    val starSystems = for (i <- 0 to starSystemCount) yield StarSystemBuilder(id)

    new Sector(parentID, id, "Alpha", Points.Origin, starSystems)
  }
}

object StarSystemBuilder extends EntityBuilder(StarSystemID) {
  def apply(parentID: String): StarSystem = {
    val id = nextID()

    val star = StarBuilder(id, currentID())

    val planetCount = 2 + Random.nextInt(2)
    val planets = for (i <- 0 to planetCount) yield PlanetBuilder(id)

    new StarSystem(parentID, id, rndName(), rndPosition(), star, planets)
  }
}

object StarBuilder extends EntityBuilder(StarID) {
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

  def apply(parentID: String, id: Int): Star = {
    new Star(parentID, s"$typeID-$id", rndName(), Vector(0, 0), rndStarClass())
  }
}

object PlanetBuilder extends EntityBuilder(PlanetID) {
  def apply(parentID: String): Planet = {
    new Planet(parentID, nextID(), rndName(), rndVector())
  }
}

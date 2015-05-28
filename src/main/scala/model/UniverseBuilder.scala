package model

import me.mtrupkin.core.Points
import model.space._

import scala.util.Random

/**
 * Created by mtrupkin on 5/26/2015.
 */
import Entity._

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

  def rndPosition(): core.Vector = core.Vector(Random.nextDouble()*40, Random.nextDouble()*20)
  def rndVector(): core.Vector = core.Vector(Random.nextDouble()*100-50, Random.nextDouble()*100-50)
}

class SectorBuilder extends EntityBuilder(SectorID) {
  def apply(): Sector = {
    val starSystemBuilder = new StarSystemBuilder
    val starSystemCount = 30 + Random.nextInt(20)
    val starSystems = for (i <- 0 to starSystemCount) yield starSystemBuilder.apply()

    val sector = new Sector(nextID(), "Alpha", Points.Origin, starSystems)
    sector
  }
}

class StarSystemBuilder extends EntityBuilder(StarSystemID) {
  def apply(): StarSystem = {
    val id = nextID()

    val planetBuilder = new PlanetBuilder
    val starBuilder = new StarBuilder(currentID())
    val star = starBuilder.apply()

    val planetCount = 2 + Random.nextInt(2)
    val planets = for (i <- 0 to planetCount) yield planetBuilder.apply()

    val starSystem = new StarSystem(id, rndName(), rndPosition(), star, planets)
    starSystem
  }
}

class StarBuilder(val id: Int) extends EntityBuilder(StarID) {
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

  def apply(): Star = {
    new Star(s"$typeID-$id", rndName(), core.Vector(0, 0), rndStarClass())
  }
}

class PlanetBuilder extends EntityBuilder(PlanetID) {
  def apply(): Planet = {
    new Planet(nextID(), rndName(), rndVector())
  }
}

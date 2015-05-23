package model

import me.mtrupkin.core.{Point, Points, Size}

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val id: String, val name: String, val position: Point, val starSystems: Seq[StarSystem]) extends DiscreteSystem {
  def typeName: String = Sector.typeName
  def update(elapsed: Int): Unit = {}

  def size: Size = Size(40, 20)

  def entities = starSystems


}

object Helper {
  def pos(): Point = Point(Random.nextInt(40), Random.nextInt(20))
  def vec(): core.Vector = core.Vector(Random.nextDouble()*100-50, Random.nextDouble()*100-50)
}

object Sector extends EntityBuilder {
  val typeName = "Sector"
  def apply(): Sector = {
    val starSystemCount = 30 + Random.nextInt(20)
    val starSystems = for (i <- 0 to starSystemCount) yield StarSystem()
    new Sector(nextID(), "Alpha", Points.Origin, starSystems)
  }
}



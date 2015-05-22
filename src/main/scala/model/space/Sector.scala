package model.space

import me.mtrupkin.core.{Size, Points, Point}
import model.Vector

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val name: String, val position: Point, val starSystems: Seq[StarSystem]) extends DiscreteSystem {
  def update(elapsed: Int): Unit = {}

  def size: Size = Size(40, 20)

  def entities: Seq[DiscreteEntity] = starSystems
}

object Helper {
  def pos(): Point = Point(Random.nextInt(40), Random.nextInt(20))
  def vec(): Vector = Vector(Random.nextDouble()*100-50, Random.nextDouble()*100-50)
}

object Sector {
  def apply(): Sector = {
    val starSystemCount = 30 + Random.nextInt(20)
    val starSystems = for (i <- 0 to starSystemCount) yield StarSystem()
    new Sector("Alpha", Points.Origin, starSystems)
  }
}


class Planet(val name: String, val position: Vector) extends BodyEntity {
  def update(elapsed: Int): Unit = {}
}

object Planet {
  def apply(): Planet = { new Planet("Alpha", Helper.vec()) }
}


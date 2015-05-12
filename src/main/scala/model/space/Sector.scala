package model.space

import me.mtrupkin.core.{Size, Points, Point}
import model.Vector

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val name: String, val position: Point, val starSystems: Seq[StarSystem]) extends DiscreteSystem {
  def update(elapsed: Int): Unit = {}

  def size: Size = Size(80, 40)

  def entities: Seq[DiscreteEntity] = starSystems
}

object Helper {
  def pos(): Point = Point(Random.nextInt(50), Random.nextInt(50))
  def vec(): Vector = Vector(Random.nextDouble()*50, Random.nextDouble()*50)
}

object Sector {
  def apply(): Sector = {
    val starSystemCount = Random.nextInt(50)
    val starSystems = for (i <- 0 to starSystemCount) yield StarSystem()
    new Sector("Alpha", Points.Origin, starSystems)
  }
}


class Planet(val name: String, val position: Vector) extends Body {
  def update(elapsed: Int): Unit = {}
}

object Planet {
  def apply(): Planet = { new Planet("Alpha", Helper.pos()) }
}


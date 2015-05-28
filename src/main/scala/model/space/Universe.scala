package model.space

import core.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */
class Universe(val sectors: Seq[Sector], val time: Long = 0) {
  def update(elapsed: Int): Unit = {
    sectors.foreach(_.update(elapsed))
  }
}

trait Entity {
  def name: String
  def id: String
  def position: Vector
  def update(elapsed: Int): Unit
}

case class Sector(name: String, id: String, position: Vector, starSystems: Seq[StarSystem]) extends Entity {
  def update(elapsed: Int): Unit = {
    starSystems.foreach(_.update(elapsed))
  }
}

case class StarSystem(name: String, id: String, position: Vector, star: Star, planets: Seq[Planet]) extends Entity {
  def update(elapsed: Int): Unit = {
    star.update(elapsed)
    planets.foreach(_.update(elapsed))
  }
}

case class Star(name: String, id: String, position: Vector, spectralType: Char) extends Entity {
  def update(elapsed: Int): Unit = {}
}

case class Planet(name: String, id: String, var position: Vector) extends Entity {
  val period = 1500
  def update(elapsed: Int): Unit = {
    val w = (2*Math.PI)/period
    val v = w*position.magnitude
    val v0 = Vector(-position.y, position.x).normal(v)
    position = position + v0
  }
}

case class Ship(name: String, id: String, position: Vector) extends Entity {
  def update(elapsed: Int): Unit = {}
}

object Entity {
  def typeName(e: Entity): String = e match {
    case _: Sector => "Sector"
    case _: StarSystem => "Star System"
    case _: Star => "Star"
    case _: Planet => "Planet"
  }

  val SectorID = "SE"
  val StarSystemID = "SS"
  val StarID = "ST"
  val PlanetID = "PL"
}
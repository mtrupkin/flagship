package model.space

import core.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */
case class Universe(sectors: Seq[Sector], time: Long = 0) extends Entity {
  val children = sectors
  val parent = Entity.RootID
  val id = "U-1"
  val name = "Universe"
  val position: Vector = Vector(0,0)

  def update(elapsed: Int): Universe = {
    new Universe(sectors.map(_.update(elapsed)), time + elapsed)
  }
}

trait Entity {
  def parent: String
  def children: Seq[Entity]
  def name: String
  def id: String
  def position: Vector
  def update(elapsed: Int): Entity

  def locate(id: String): Entity = {
    def locate(acc: Seq[Entity]): Entity = {
      acc match {
        case head :: tail => if (head.id == id) head else locate(tail ++ head.children)
        case Nil => ???
      }
    }
    locate(Seq(this))
  }
}

case class Sector(parent: String, id: String, name: String, position: Vector, starSystems: Seq[StarSystem]) extends Entity {
  def children = starSystems
  def update(elapsed: Int): Sector = {
    copy(starSystems= starSystems.map(_.update(elapsed)))
  }
}

case class StarSystem(parent: String, id: String, name: String, position: Vector, star: Star, planets: Seq[Planet]) extends Entity {
  def children = star +: planets

  def update(elapsed: Int): StarSystem = {
    copy(star = star.update(elapsed), planets = planets.map(_.update(elapsed)))
  }
}

case class Star(parent: String, id: String, name: String, position: Vector, spectralType: Char) extends Entity {
  def children = Nil
  def update(elapsed: Int): Star = this
}

case class Planet(parent: String, id: String, name: String, position: Vector) extends Entity {
  def children = Nil
  val period = 1500
  def update(elapsed: Int): Planet = {
    val w = (2*Math.PI)/period
    val v = w*position.magnitude
    val v0 = Vector(-position.y, position.x).normal(v)
    copy(position = position + v0)
  }
}

case class Ship(parent: String, id: String, name: String, position: Vector) extends Entity {
  def children = Nil
  def update(elapsed: Int): Ship = this
}

object Entity {
  def typeName(e: Entity): String = e match {
    case _: Sector => "Sector"
    case _: StarSystem => "Star System"
    case _: Star => "Star"
    case _: Planet => "Planet"
  }

  // change to ""
  val RootID = "U"

  val UniverseID = "U"
  val SectorID = "SE"
  val StarSystemID = "SS"
  val StarID = "ST"
  val PlanetID = "PL"
}
package model.space

import core.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def name: String
  def id: String
  def position: Vector
  def update(elapsed: Int): Entity
}

case class Universe(sectors: Seq[Sector], ships: Seq[Ship], time: Long = 0) extends Entity {
  val id = "U"
  val name = "Universe"
  val position: Vector = Vector(0,0)

  def update(elapsed: Int): Universe = {
    new Universe(sectors.map(_.update(elapsed)), ships.map(_.update(elapsed)), time + elapsed)
  }

  def locate(id: String): Entity = {
    def locate(acc: Seq[Entity]): Entity = {
      acc match {
        case (branch:EntityNode) +: tail => if (branch.id == id) branch else locate(branch.children ++ tail)
        case (entity:Entity) +: tail => if (entity.id == id) entity else locate(tail)
        case _ => ???
      }
    }
    locate(this +: sectors)
  }
}

trait EntityLeaf extends Entity {
  def parent: String
}

trait EntityNode extends Entity {
  def parent: String
  def children: Seq[Entity]
}

case class Sector(parent: String, id: String, name: String, position: Vector, starSystems: Seq[StarSystem]) extends EntityNode {
  def children = starSystems
  def update(elapsed: Int): Sector = {
    copy(starSystems= starSystems.map(_.update(elapsed)))
  }
}

case class StarSystem(parent: String, id: String, name: String, position: Vector, star: Star, planets: Seq[Planet]) extends EntityNode {
  def children = star +: planets

  def update(elapsed: Int): StarSystem = {
    copy(star = star.update(elapsed), planets = planets.map(_.update(elapsed)))
  }
}

case class Star(parent: String, id: String, name: String, position: Vector, spectralType: Char) extends EntityLeaf {
  def update(elapsed: Int): Star = this
}

case class Planet(parent: String, id: String, name: String, position: Vector) extends EntityLeaf {
  val period = 20000
  def update(elapsed: Int): Planet = {
    val w = (2*Math.PI)/period
    val v = w*position.magnitude
    val v0 = Vector(-position.y, position.x).normal(v)
    copy(position = position + v0)
  }
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
  val ShipID = "SP"
  val SectorID = "SE"
  val StarSystemID = "SS"
  val StarID = "ST"
  val PlanetID = "PL"
}
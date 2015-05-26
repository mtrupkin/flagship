package model

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val id: String, val name: String, val position: core.Vector, val starSystems: Seq[StarSystem]) extends EntitySystem {
  def update(elapsed: Int): Unit = {}
  def entities = starSystems
}

object Sector  {
  val typeName = "Sector"
  val typeID = "SE"
}



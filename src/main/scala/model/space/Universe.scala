package model.space

/**
 * Created by mtrupkin on 12/19/2014.
 */
class Universe(val sectors: Seq[Sector]) {
  var time: Long = 0

  def update(elapsed: Int) {
    time += elapsed
    sectors.foreach(_.update(elapsed))
  }
}

class Sector(val id: String, val name: String, val position: core.Vector, val starSystems: Seq[StarSystem]) extends Entity

object Sector  {
  val typeName = "Sector"
  val typeID = "SE"
}

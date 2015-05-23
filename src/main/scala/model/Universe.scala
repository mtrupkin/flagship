package model



/**
 * Created by mtrupkin on 12/19/2014.
 */

class Universe(val sector: Sector) {
  var time: Long = 0

  val sectors: Seq[Sector] = List(sector)

  def update(elapsed: Int) {
    time += elapsed
    sectors.foreach(_.update(elapsed))
  }
}
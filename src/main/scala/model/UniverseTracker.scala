package model

import me.mtrupkin.console.{Colors, ScreenChar, Screen}
import me.mtrupkin.core.{Points, Point}
import model.space.{StarSystem, Universe}
import util.{Path, AStar}


/**
 * Created by mtrupkin on 5/3/2015.
 */
trait UniverseTracker {
  self: Universe =>

  var target: Point = Points.Origin

  def render(screen: Screen): Unit = {
    sector.render(screen)
  }

  val starSystem = StarSystem()

  def render2(screen: Screen): Unit = {
    starSystem.render(screen)
  }
}

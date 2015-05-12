package model

import me.mtrupkin.console.{Colors, ScreenChar, Screen}
import me.mtrupkin.core.{Points, Point}
import model.space.{ViewableDiscreteSystem, ViewableBodySystem, StarSystem, Universe}
import util.{Path, AStar}


/**
 * Created by mtrupkin on 5/3/2015.
 */
trait UniverseTracker {
  self: Universe =>

  var target: Point = Points.Origin

  val viewableSector = new ViewableDiscreteSystem(sector)

  def render(screen: Screen): Unit = {

    viewableSector.render(screen)
  }

  val starSystem = new ViewableBodySystem(StarSystem())

  def render2(screen: Screen): Unit = {
    starSystem.render(screen)
  }
}

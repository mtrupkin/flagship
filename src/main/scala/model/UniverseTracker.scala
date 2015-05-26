package model

import me.mtrupkin.console.{Colors, ScreenChar, Screen}
import me.mtrupkin.core.{Points, Point}
import model._
import util.{Path, AStar}


/**
 * Created by mtrupkin on 5/3/2015.
 */
trait UniverseTracker {
  self: Universe =>

  var target1: Point = Points.Origin
  var target2: Point = Points.Origin

  val initialSector = new SectorBuilder().apply()
  val initialStarSystem = sector.entities(0)

  var entity1: Entity = initialSector
  var entity2: Entity = initialStarSystem
}

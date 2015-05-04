package model

import me.mtrupkin.console.{Colors, ScreenChar, Screen}
import me.mtrupkin.core.{Points, Point}
import util.{Path, AStar}


/**
 * Created by mtrupkin on 5/3/2015.
 */
class WorldTracker extends World {
  var target: Point = Points.Origin

  val pathFinder = new AStar(this, this.size)
  val pathChar = new ScreenChar(Floor.char, Colors.Yellow)
  def renderPath(screen: Screen, agent: Agent, target: Point): Unit = {
    val path = pathFinder.search(agent, target, 100)
    val smoothPoints = Path.smoothPathPoints(path, this)

    smoothPoints.foreach(screen(_) = pathChar)
  }
}

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

  var entitySystem1: EntityControl = new DiscreteSystemControl(sector)
  var entitySystem2: EntityControl = new BodySystemControl(StarSystem())


  def highlightTarget1(p: Point): Unit = {
    entitySystem1.target(p) match {
      case Some(view) => {
//        view2 = view
      }
      case _ =>
    }
  }
  def selectTarget1(screen1: Screen, screen2: Screen, p: Point): Unit = {
//    view1.selectView(screen1.size, p) match {
//      case Some(view) => {
//        screen1.clear()
//        screen2.clear()
//        view1 = view
////        view2 = view
//      }
//      case _ =>
//    }
  }

  def selectTarget2(p: Point): Unit = {
  }
}

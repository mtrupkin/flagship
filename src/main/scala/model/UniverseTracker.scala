package model

import me.mtrupkin.console.{Colors, ScreenChar, Screen}
import me.mtrupkin.core.{Points, Point}
import model.space._
import util.{Path, AStar}


/**
 * Created by mtrupkin on 5/3/2015.
 */
trait UniverseTracker {
  self: Universe =>

  var target1: Point = Points.Origin
  var target2: Point = Points.Origin

  var view1: ViewableSystem  = new ViewableDiscreteSystem(sector)
  var view2: ViewableSystem = new ViewableBodySystem(StarSystem())

  def render(screen: Screen): Unit = {
    view1.render(screen)
  }

  def render2(screen: Screen): Unit = {
    view2.render(screen)
  }

  def highlightTarget1(p: Point): Unit = {
    view1.target(p) match {
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

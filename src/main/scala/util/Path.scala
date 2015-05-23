package util

import core.GameMap
import me.mtrupkin.core.Point
import me.mtrupkin.path.Line

import scala.collection.immutable.List

/**
 * Created by mtrupkin on 5/3/2015.
 */
object Path {
  import Line.bresenham
  def hasLineOfMovement(p: Point, p0: Point, map: GameMap): Boolean = {
    val line = bresenham(p, p0)
    val cost = map(p).cost
    val res = line.forall(p1=> map(p1).cost == cost)
    res
  }

  def wayPoints(path: Seq[Point], map: GameMap): Seq[Point] = {

    def wayPoints(p0: Point, path: Seq[Point], smoothed: List[Point]): Seq[Point] = {
      // returns smooth path up to point p0
      // finds the line from p0 to a point on previousPath that is not blocked
      // returns that point and the rest of the path
      // param next is the last point that had a line of sight to p0
      def smoothPath(p0: Point, next: Point, previousPath: List[Point]): List[Point] = {
        previousPath match {
          case p :: ps => if (hasLineOfMovement(p, p0, map))
            smoothPath(p0, p, ps)
          else
            next :: previousPath
          case Nil => next :: Nil
        }
      }

      path match {
        case p :: ps => wayPoints(p, ps, smoothPath(p, p0, smoothed))


        case Nil => smoothed
      }
    }

    // XXX: try to remove reverse
    path.reverse match {
      case Nil => Nil
      case p::ps => wayPoints(p, ps, Nil)
    }
  }

  def smoothPathPoints(path: Seq[Point], map: GameMap): Seq[Point] = {
    def smoothPathPoints(p0: Point, path: Seq[Point], smoothed: List[Point]): Seq[Point] = {
      // returns smooth path up to point p0
      // finds the line from p0 to a point in previousPath that is not blocked
      // returns the line and the rest of the path
      // param next is the last point that had a line of sight to p0
      def smoothPreviousPath(p0: Point, next: Point, previousPath: List[Point]): List[Point] = {
        previousPath match {
          case p :: ps => if (hasLineOfMovement(p, p0, map))
            smoothPreviousPath(p0, p, ps)
          else
            p0 :: (bresenham(next, p0) ++ previousPath).toList
          case Nil => p0 :: bresenham(next, p0).toList
        }
      }

      path match {
        case p :: ps => smoothPathPoints(p, ps, smoothPreviousPath(p, p0, smoothed))
        // XXX: try to remove reverse and tail call
        case Nil => smoothed.reverse.tail
      }
    }

    path match {
      case Nil => Nil
//      case p::Nil => path
      case p::ps => smoothPathPoints(p, ps, Nil)
    }
  }
}

package util

import core.GameMap
import me.mtrupkin.core.{Point, Size}


import scala.Array._
import scala.collection.mutable

/**
 * Created by mtrupkin on 12/31/2014.
 */
class AStar(val map: GameMap, val size: Size) {
  class Node(val p: Point) extends Ordered[Node] {
    // path cost
    var cost = 0.0d
    // parent of this node, how we reached it in the search
    var parent: Option[Node] = None
    // heuristic cost
    var heuristic = 0.0d
    // search depth
    var depth = 0
    // in open list
    var open = false
    // in closed list
    var closed = false

    def setParent(newParent: Node): Int = {
      parent = Some(newParent)
      depth = newParent.depth + 1

      depth
    }

    def reset(): Unit = {
      closed = false
      open = false
      cost = 0
      depth = 0
    }

    override def compare(o: Node): Int = {
      val f = heuristic + cost
      val of = o.heuristic + o.cost
      Math.signum(of-f).toInt
    }

    override def toString: String = {
      s"$p open: $open closed: $closed cost: $cost heuristic: $heuristic"
    }
  }
//  val size = tileMap.size
  protected val nodes = ofDim[Node](size.width, size.height)
  protected def nodes(p: Point): Node = nodes(p.x)(p.y)
  size.foreach(p => nodes(p.x)(p.y) = new Node(p))

  // nodes that have been searched through
  protected val closed = mutable.ListBuffer.empty[Node]

  // nodes that we do not yet consider fully searched
  protected var open = new mutable.PriorityQueue[Node]()

  /** searches for a path from the agent's position to point p
    * agent's starting position is included in the returned path
    * the returned path is generally one of many cost equivalent optimal paths
    * if the destination is not reachable, an empty list is returned
    * @param p0 object that is moving
    * @param p destination of search
    * @param maxSearchDistance furthest distance to search
  */
  def search(p0: Point, p: Point, maxSearchDistance: Int = 100): Seq[Point] = {
    // cannot move to target
    if (!map(p).move) return Nil

    // already at target
    if (p == p0) return Seq(p)

    // initial state for A*. The closed group is empty. Only the starting
    // tile is in the open list and it's cost is zero, i.e. we're already there
    var distance = 0
    val startNode = nodes(p0)
    startNode.cost = 0
    startNode.depth = 0

    closed.clear
    open.clear
    addToOpen(startNode)
    size.foreach(p => nodes(p.x)(p.y).reset)

    val targetNode = nodes(p)
    targetNode.parent = None


    // while we haven't found the goal and haven't exceeded our max search depth
    var maxDepth = 0

    // node currently searching from
    var current: Node = null

    def process(): Unit = {
      while ((maxDepth < maxSearchDistance) && (open.length != 0)) {
        // pull out the first node in our open list, this is determined to
        // be the most likely to be the next step based on our heuristic
        current = open.dequeue()
        val pl = current.p

        distance = current.depth

        if (current == targetNode) return

        removeFromOpen(current)
        addToClosed(current)

        // search through all the neighbours of the current node evaluating
        // them as next steps
        for {
          x <- -1 to 1
          y <- -1 to 1
          if !((x == 0) && (y == 0))
        } {
          // determine the location of the neighbour and evaluate it
          val np = Point(x + current.p.x, y + current.p.y)
          if (map(np).move) {
            // the cost to get to this node is cost the current plus the movement
            // cost to reach this node. Note that the heuristic value is only used
            // in the sorted open list
            val nextStepCost = current.cost + map(np).cost
            val neighbour = nodes(np)

            // if the new cost we've determined for this node is lower than
            // it has been previously makes sure the node hasn't been discarded. We've
            // determined that there might have been a better path to get to
            // this node so it needs to be re-evaluated
            if (nextStepCost < neighbour.cost) {
              if (neighbour.open) {
                removeFromOpen(neighbour)
              }
              if (neighbour.closed) {
                removeFromClosed(neighbour)
              }
            }

            // if the node hasn't already been processed and discarded then
            // reset it's cost to our current cost and add it as a next possible
            // step (i.e. to the open list)
            if (!neighbour.open && !neighbour.closed) {
              neighbour.cost = nextStepCost
              neighbour.heuristic = heuristic(np, p)
              maxDepth = Math.max(maxDepth, neighbour.setParent(current))
              addToOpen(neighbour)
            }
          }
        }
      }
    }
    process()

    // since we've got an empty open list or we've run out of search
    // there was no path. Just return null
    if (targetNode.parent == None) {
      return Nil
    }

    // At this point we've definitely found a path so we can uses the parent
    // references of the nodes to find out way from the target location back
    // to the start recording the nodes on the way.
    def walk(path: List[Point], current: Node): List[Point] = {
      if (current != startNode) {
        walk(current.p :: path, current.parent.getOrElse(startNode))
      } else current.p :: path
    }

    walk(List(), targetNode).toSeq
  }

  def heuristic(p0: Point, p: Point): Double = {
    val d = p - p0
    Math.sqrt((d.x * d.x) + (d.y * d.y))
  }

  // add a node to the open list
  protected def addToOpen(node: Node) {
    node.open = true
    open += node
  }

  // remove a node from the open list
  protected def removeFromOpen(node: Node) {
    node.open = false
    open = open.filterNot(_ == node)
  }

  // add a node to the closed list
  protected def addToClosed(node: Node) {
    node.closed = true
    closed += node
  }

  // remove a node from the closed list
  protected def removeFromClosed(node: Node) {
    node.closed = false
    closed -= node
  }
}

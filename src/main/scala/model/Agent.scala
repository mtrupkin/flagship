package model

import me.mtrupkin.core.{StateMachine, Point}
import model.space._
import util.{Path, AStar}

/**
 * Created by mtrupkin on 4/23/2015.
 */

class Site(val position: Point, var store: Int)
class Home(val position: Point, var store: Int)

trait Agent extends StateMachine {
  self =>
  type StateType = AgentState

  var position: Vector
  def speed: Double
  def update(elapsed: Int, universe: Universe): Unit = currentState.update(elapsed, universe)

  trait AgentState extends State {
    def update(elapsed: Int, universe: Universe): Unit
  }
}


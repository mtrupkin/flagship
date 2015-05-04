package model

import me.mtrupkin.core.{StateMachine, Point}
import play.api.libs.json.Json
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
  def update(elapsed: Int, world: World): Unit = currentState.update(elapsed, world)

  trait AgentState extends State {
    def update(elapsed: Int, world: World): Unit
  }

  class Arrive(val destination: Vector, arrived: (World) => AgentState) extends AgentState {
    import Vector._

    def update(elapsed: Int, world: World): Unit = {
      val pathFinder = new AStar(world, world.size)
      val path = pathFinder.search(self, destination)
      val wayPoints = Path.wayPoints(path, world)
      val nextWayPoint = wayPoints.head

      val distanceToDestination = (destination - position).magnitude
      if (distanceToDestination < 1.0) changeState(arrived(world)) else {
        val desired = toVector(nextWayPoint) - position
        val costPercent = (100 - world(toPoint(position)).cost) / 100.0 + 1
        update(desired.normal(((speed * elapsed ) / 1000)*costPercent), world)
      }
    }

    def update(v: Vector, world: World): Unit = {
      val newPosition = position + v
       if (toPoint(newPosition) != toPoint(position)) {
         if (  world(position).cost > 1) world(position).cost = world(position).cost -1
       }
      position = newPosition
    }
  }
}

class Worker(var position: Vector, val destination: Site) extends Agent {
  var maxPayload: Int = 5
  var payload: Int = 0
  val speed: Double = 0.2
  lazy val initialState: AgentState = new Arrive(destination.position, arrivedSite)

  def arrivedSite(world: World): AgentState = {
    destination.store = destination.store - maxPayload
    payload = maxPayload
    new Arrive(world.home.position, arrivedHome)
  }

  def arrivedHome(world: World): AgentState = {
    world.home.store = world.home.store + payload
    payload = 0

    new Arrive(destination.position, arrivedSite)
  }

}


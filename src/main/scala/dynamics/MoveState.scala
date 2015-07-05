package dynamics

import model.space.{Coordinate, Ship}

/**
 * Created by mtrupkin on 7/4/2015.
 */

trait MoveState {
  def update(self: Ship, elapsed: Int, dt: Int): Ship
}

object Stop extends MoveState {
  def update(self: Ship, elapsed: Int, dt: Int): Ship = self
}

case class Arrive(val target: Coordinate) extends MoveState {
  def update(self: Ship, elapsed: Int, dt: Int): Ship = {
    val desiredVelocity = target.position - self.position
    if (desiredVelocity.magnitude <= 1.0) {
      self.copy(moveState = Stop)
    } else {
      val velocity = desiredVelocity.normal(0.5)
      self.copy(position = self.position + velocity)
    }
  }
}

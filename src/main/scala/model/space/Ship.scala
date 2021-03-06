package model.space


import core.Vector
import dynamics.{MoveState, Stop}
import me.mtrupkin.core.{StateMachine, Points}
import model.EntityBuilder
import model.space.Entity._

/**
 * Created by mtrupkin on 6/29/2015.
 */
case class Ship(parent: String, id: String,
  name: String,
  position: Vector,
  shields: Int,
  weapons: Seq[Weapon],
  moveState: MoveState = Stop) extends EntityLeaf {
  def update(elapsed: Int): Ship = moveState.update(this, elapsed, 0)
}

case class Weapon(name: String, attack: Int)

object ShipBuilder extends EntityBuilder(ShipID) {
  def apply(parentID: String): Ship = {
    val id = nextID()
    val weapons = Seq(Weapon("Phaser-1", 1), Weapon("Phaser-2", 1), Weapon("Photons", 2))
    Ship(parentID, id, "Enterprise", Vector(-90, 90), 10, weapons)
  }
}
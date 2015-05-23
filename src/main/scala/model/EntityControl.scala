package model

import me.mtrupkin.console.{ScreenChar, Screen}
import me.mtrupkin.core.{Size, Point}
import model.DiscreteSystem

/**
 * Created by mtrupkin on 5/22/2015.
 */

trait EntityControl {
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
}

object EntityControl {
  def apply(entity: Entity): EntityControl = entity match {
    case e: DiscreteSystem => new DiscreteSystemControl(e)
    case e: BodySystem => new BodySystemControl(e)
    case _ => new EntityControl {
      def render(screen: Screen): Unit = {}
      def target(p: Point): Option[Entity] = None
    }
  }


  def draw(entity: Entity): ScreenChar = entity match {
    case _: StarSystem => '*'
    case _: Star => '*'
    case _: Planet => 'O'
    case _ => ' '
  }
}

trait EntitySystemControl extends EntityControl {
  def entitySystem: EntitySystem

  def entities: Seq[Entity] = entitySystem.entities

  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(position) = EntityControl.draw(entity)
  }

  def target(p: Point): Option[Entity] = entities.find( _.position == p)
}

class DiscreteSystemControl(val entitySystem: DiscreteSystem) extends EntitySystemControl {

}

class BodySystemControl(val entitySystem: BodySystem) extends EntitySystemControl {
  val consoleSize: Size = Size(40, 20)

  def toConsole(v: core.Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = core.Vector(v.x*rx, v.y*ry) / entitySystem.radius

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    val p = Point(r.x + rx, r.y + ry)
    p
  }

//  def target(p: Point): Option[Entity] = entities.find(e => toConsole(e.position) == p)

//  def render(screen: Screen): Unit = {
//    for {
//      entity <- entities
//      position = entity.position
//    } screen(toConsole(position)) = EntityControl.draw(entity)
//  }

}

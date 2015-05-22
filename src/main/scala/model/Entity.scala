package model.space

import me.mtrupkin.console.{ScreenChar, Screen}
import me.mtrupkin.core.{Size, Point}
import model.Vector

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def name: String
}

trait DiscreteEntity extends Entity {
  def position: Point
}

trait BodyEntity extends Entity {
  def position: Vector
}

trait DiscreteSystem extends Entity {
  def size: Size
  def entities: Seq[DiscreteEntity]
}

trait BodySystem extends Entity  {
  def radius: Int
  def entities: Seq[BodyEntity]
}

object Entity {
  def draw(entity: Entity): ScreenChar = entity match {
    case _: StarSystem => '*'
    case _: Star => '*'
    case _: Planet => 'O'
    case _ => ' '
  }
}

trait ViewableSystem {
  def entity: Entity
  def render(screen: Screen): Unit
  def selectTarget(p: Point): Option[ViewableSystem]
  def target(p: Point): Option[Entity]
}

class ViewableDiscreteSystem(val discreteSystem: DiscreteSystem) extends ViewableSystem {
  import discreteSystem._

  val entity = discreteSystem

  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(position) = Entity.draw(entity)
  }

  def target(p: Point): Option[Entity] = entities.find( _.position == p)
  
  def selectTarget(p: Point): Option[ViewableSystem] = {
    val selected = target(p)
    selected match {
      case Some(e: DiscreteSystem) => Some(new ViewableDiscreteSystem(e))
      case Some(e: BodySystem) => Some(new ViewableBodySystem(e))
      case _ => None
    }
  }
}

class ViewableBodySystem(val bodySystem: BodySystem) extends ViewableSystem {
  import bodySystem._

  val entity = bodySystem
  val consoleSize: Size = Size(40, 20)

  def toConsole(v: Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = Vector(v.x*rx, v.y*ry) / radius

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    val p = Point(r.x + rx, r.y + ry)
    p
  }

  def target(p: Point): Option[Entity] = entities.find(e => toConsole(e.position) == p)

  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(toConsole(position)) = Entity.draw(entity)
  }

  def selectTarget(p: Point): Option[ViewableSystem] = {
    val selected = target(p)
    selected match {
      case Some(e: DiscreteSystem) => Some(new ViewableDiscreteSystem(e))
      case Some(e: BodySystem) => Some(new ViewableBodySystem(e))
      case _ => None
    }
  }
}

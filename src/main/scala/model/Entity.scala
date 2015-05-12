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

trait Body extends Entity {
  def position: Vector
}

trait DiscreteSystem {
  def size: Size
  def entities: Seq[DiscreteEntity]
}

trait BodySystem {
  def radius: Int
  def entities: Seq[Body]
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
  def render(screen: Screen): Unit
}

class ViewableDiscreteSystem(val discreteSystem: DiscreteSystem) extends ViewableSystem {
  import discreteSystem._

  def render(screen: Screen): Unit = {
    def toScreen(p0: Point): Point = {
      val rx = screen.size.width
      val ry = screen.size.height

      Point(p0.x/rx, p0.y/ry)
    }

    for {
      entity <- entities
      position = entity.position
    } screen(toScreen(position)) = Entity.draw(entity)
  }
}

class ViewableBodySystem(val bodySystem: BodySystem) extends ViewableSystem {
  import bodySystem._

  def render(screen: Screen): Unit = {
    def toScreen(v: Vector): Point = {
      val rx = screen.size.width / 2
      val ry = screen.size.height / 2

      val r =  v / radius

      implicit def toInt(d: Double): Int = Math.floor(d).toInt
      Point(rx/r.x, ry/r.y)
    }

    for {
      entity <- entities
      position = entity.position
    } screen(toScreen(position)) = Entity.draw(entity)
  }
}

trait ViewableEntity {
  def screenSize: Size
  def radius: Int

  def draw(p: Point): ScreenChar

  def toScreen(v: Vector): Point = {
    val rx = screenSize.width / 2
    val ry = screenSize.height / 2

    val r =  v / radius

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    Point(rx/r.x, ry/r.y)
  }
}

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
  def selectView(screenSize: Size, p: Point): Option[ViewableSystem]
}

class ViewableDiscreteSystem(val discreteSystem: DiscreteSystem) extends ViewableSystem {
  import discreteSystem._

  def toScreen(screenSize: Size, p0: Point): Point = {
    val rx = screenSize.width/size.width
    val ry = screenSize.height/size.height

    Point(p0.x/rx, p0.y/ry)
  }

  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(toScreen(screen.size, position)) = Entity.draw(entity)
  }

  def selectView(screenSize: Size, p: Point): Option[ViewableSystem] = {
    val selected = entities.find( e => toScreen(screenSize, e.position) == p)
    selected match {
      case Some(e: DiscreteSystem) => Some(new ViewableDiscreteSystem(e))
      case Some(e: BodySystem) => Some(new ViewableBodySystem(e))
      case _ => None
    }
  }
}

class ViewableBodySystem(val bodySystem: BodySystem) extends ViewableSystem {
  import bodySystem._

  def toScreen(screenSize: Size, v: Vector): Point = {
    val rx = screenSize.width / 2
    val ry = screenSize.height / 2

    val r = Vector(v.x*rx, v.y*ry) / radius

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    val p = Point(r.x + rx, r.y + ry)
    p
  }


  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(toScreen(screen.size, position)) = Entity.draw(entity)
  }

  def selectView(screenSize: Size, p: Point): Option[ViewableSystem] = {
    val selected = entities.find(e => toScreen(screenSize, e.position) == p)
    selected match {
      case Some(e: DiscreteSystem) => Some(new ViewableDiscreteSystem(e))
      case Some(e: BodySystem) => Some(new ViewableBodySystem(e))
      case _ => None
    }
  }
}

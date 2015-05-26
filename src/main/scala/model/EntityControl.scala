package model

import me.mtrupkin.console.{Colors, RGB, Screen, ScreenChar}
import me.mtrupkin.core.{Point, Size}

/**
 * Created by mtrupkin on 5/22/2015.
 */

trait EntityControl {
  def entity: Entity
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
  implicit def toInt(d: Double): Int = Math.floor(d).toInt
  def toScreen(v: core.Vector): Point = v
}

object EntityControl {
  def apply(entity: Entity): EntityControl = entity match {
    case starSystem: StarSystem => new StarSystemControl(starSystem)
    case es: EntitySystem => new EntitySystemControl {
      val entity = es
    }

    case e => new EntityControl {
      val entity = e
      val consoleSize: Size = Size(40, 20)

      override def toScreen(v: core.Vector): Point = {
        val rx = consoleSize.width / 2
        val ry = consoleSize.height / 2

        val r = core.Vector(v.x*rx, v.y*ry) / 100

        implicit def toInt(d: Double): Int = Math.floor(d).toInt
        val p = Point(r.x + rx, r.y + ry)
        p
      }
      def render(screen: Screen): Unit = screen(toScreen(core.Vector(0, 0))) = draw(entity)
      def target(p: Point): Option[Entity] =  if (toScreen(core.Vector(0, 0)) == p) Some(entity) else None
    }
  }

  def draw(entity: Entity): ScreenChar = entity match {
    case starSystem: StarSystem => ScreenChar('*', StarClass.color(starSystem.star.spectralType))
    case star: Star => ScreenChar('*', StarClass.color(star.spectralType))
    case _: Planet => 'O'
    case _ => ' '
  }
}

trait EntitySystemControl extends EntityControl {
  def entities: Seq[Entity] = entity match {
    case es: EntitySystem => es.entities
    case _ => ???
  }

  def render(screen: Screen): Unit = {
    for {
      entity <- entities
      position = entity.position
    } screen(toScreen(position)) = EntityControl.draw(entity)
  }

  def target(p: Point): Option[Entity] = entities.find( e => toScreen(e.position) == p)
}

class StarSystemControl(val starSystem: StarSystem) extends EntitySystemControl {
  val entity = starSystem
  val consoleSize: Size = Size(40, 20)

  override def toScreen(v: core.Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = core.Vector(v.x*rx, v.y*ry) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    val p = Point(r.x + rx, r.y + ry)
    p
  }
}


object StarClass {
  def color(starClass: Char): RGB = {
    starClass match {
      case 'O' => Colors.White
      case 'B' => Colors.Yellow
      case 'A' => Colors.Red
      case 'F' => Colors.Green
      case 'G' => Colors.LightBlue
      case 'K' => RGB(255, 0, 255)
      case 'M' => Colors.Blue
    }
  }
}
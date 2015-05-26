package model

import me.mtrupkin.console.{Colors, RGB, ScreenChar, Screen}
import me.mtrupkin.core.{Size, Point}

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
//    case sector: Sector => new StarSystemControl(starSystem)
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
      def render(screen: Screen): Unit = screen(toScreen(entity.position)) = draw(entity)
      def target(p: Point): Option[Entity] =  if (toScreen(entity.position) == p) Some(entity) else None
    }
  }

  def draw(entity: Entity): ScreenChar = entity match {
    case starSystem: StarSystem => ScreenChar('*', starColor(starSystem.star.starClass))
    case star: Star => ScreenChar('*', starColor(star.starClass))
    case _: Planet => 'O'
    case _ => ' '
  }

  def starColor(starClass: Char): RGB = {
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

//  def target(p: Point): Option[Entity] = entities.find(e => toConsole(e.position) == p)

//  def render(screen: Screen): Unit = {
//    for {
//      entity <- entities
//      position = entity.position
//    } screen(toConsole(position)) = EntityControl.draw(entity)
//  }

}

package model

import me.mtrupkin.console.{Colors, RGB, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Point, Size}
import model.space._

/**
 * Created by mtrupkin on 5/22/2015.
 */
trait EntityViewer {
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
}

object EntityViewer {
  def apply(entity: Entity): EntityViewer = entity match {
    case sector: Sector => new EntitySystemViewer {
      def entities: Seq[Entity] = sector.children
    }

    case starSystem: StarSystem => new StarSystemViewer(starSystem)
    case planet: Planet => new PlanetViewer(planet)
    case e => new EntityViewer {
      val consoleSize: Size = Size(40, 20)

       def toScreen(v: core.Vector): Point = {
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
    case starSystem: StarSystem => ScreenChar('*', starColor(starSystem.star.spectralType))
    case star: Star => ScreenChar('*', starColor(star.spectralType))
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

trait EntitySystemViewer extends EntityViewer {
  def entities: Seq[Entity]
  def toScreen(v: core.Vector): Point = v

  def render(screen: Screen): Unit = {
    entities.foreach {
      e => screen(toScreen(e.position)) = EntityViewer.draw(e)
    }
  }

  def target(p: Point): Option[Entity] = {
    entities.find( e => toScreen(e.position) == p)
  }
}

class SectorViewer(val sector: Sector) extends EntitySystemViewer  {
  def entities: Seq[Entity] = sector.starSystems
}

class StarSystemViewer(val starSystem: StarSystem) extends EntitySystemViewer {
  val consoleSize: Size = Size(40, 20)
  val entities = Seq(starSystem.star) ++ starSystem.planets

  override def toScreen(v: core.Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = core.Vector(v.x*rx, v.y*ry) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    Point(r.x + rx, r.y + ry)
  }
}

class PlanetViewer(val planet: Planet) extends EntityViewer {
  val consoleSize: Size = Size(40, 20)
  val dx = consoleSize.width / 2
  val dy = consoleSize.height / 2


  def toScreen(v: core.Vector): Point = {
    val r = core.Vector(v.x*dx, v.y*dy) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    Point(r.x + dx, r.y + dy)
  }

  def viewerToScreen(p: Point): Point = (p.x + dx, -p.y + dy)

  def render(screen: Screen): Unit = {
//    for {
//      x <- 0 until consoleSize.width
//      y <- 0 until consoleSize.height
//    } {
//      screen(Point(dx, y)) = '.'
//      screen(Point(x, dy)) = '.'
//    }
    val r = 6
    for {
      x0 <- -r to r
      y0 <- -r to r
      if (core.Vector.toVector(x0, y0).magnitude < r)
      p1 = viewerToScreen(x0, y0)
    } screen(p1) = '.'
  }

  def target(p: Point): Option[Entity] = None
}


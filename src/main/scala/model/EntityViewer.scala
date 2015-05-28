package model

import me.mtrupkin.console.{Colors, RGB, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Point, Size}
import model.space._

/**
 * Created by mtrupkin on 5/22/2015.
 */
trait EntityViewer {
  def parent: EntityViewer
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
}

object EntityViewer {
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
  def apply(currentViewer: EntityViewer, entity: Entity): EntityViewer = entity match {
    case sector: Sector => new EntityViewer {
      def parent: EntityViewer = this
      override def target(p: Point): Option[Entity] = ???

      override def render(screen: Screen): Unit = ???
    }
    case starSystem: StarSystem => new StarSystemViewer(currentViewer, starSystem)
    case planet: Planet => new PlanetViewer(currentViewer, planet)
    case e => new EntityViewer {
      val parent = currentViewer
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
}

trait EntitySystemViewer extends EntityViewer {
  def entities: Seq[Entity]
  def toScreen(v: core.Vector): Point

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
  def parent: EntityViewer = this
  def entities: Seq[Entity] = sector.starSystems
  def toScreen(v: core.Vector): Point = v
}

class StarSystemViewer(val parent: EntityViewer, val starSystem: StarSystem) extends EntitySystemViewer {
  val consoleSize: Size = Size(40, 20)
  val entities = Seq(starSystem.star) ++ starSystem.planets

  def toScreen(v: core.Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = core.Vector(v.x*rx, v.y*ry) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    val p = Point(r.x + rx, r.y + ry)
    p
  }
}

class PlanetViewer(val parent: EntityViewer, val planet: Planet) extends EntityViewer {
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


package model

import me.mtrupkin.console.{Colors, RGB, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Point, Size}
import model.space._

/**
 * Created by mtrupkin on 5/22/2015.
 */

object EntityViewer {
  def apply(entity: Entity, ships: Seq[Ship]): EntityViewer = {
    entity match {
      case sector: Sector => new SectorViewer(sector, ships)
      case starSystem: StarSystem => new StarSystemViewer(starSystem, ships)
      case planet: Planet => new PlanetViewer(planet, ships)
      case star: Star => ???
    }
  }

  def draw(entity: Entity): ScreenChar = entity match {
    case starSystem: StarSystem => draw(starSystem.star)
    case star: Star => draw(star)
    case _: Planet => 'O'
    case _ => ' '
  }

  def draw(star: Star): ScreenChar = ScreenChar('*', starColor(star.spectralType))

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

trait EntityViewer {
  val consoleSize: Size = Size(40, 20)
  def ships: Seq[Ship]
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
}

trait EntitySystemViewer extends EntityViewer {
  def entities: Seq[Entity]

  def draw(entity: Entity): ScreenChar = {
    val sc = EntityViewer.draw(entity)
    if (ships.exists(ship => entity.id == ship.parent)) {
      //sc.copy(bg = Colors.LightGrey)
      sc.copy(fg = RGB(255,165,0))
    } else sc
  }

  def toScreen(v: core.Vector): Point = v

  def render(screen: Screen): Unit = {
    entities.foreach {
      e => screen(toScreen(e.position)) = draw(e)
    }
  }

  def target(p: Point): Option[Entity] = {
    entities.find( e => toScreen(e.position) == p)
  }
}

class SectorViewer(val sector: Sector, val ships: Seq[Ship]) extends EntitySystemViewer  {
  def entities: Seq[Entity] = sector.starSystems
}

class StarSystemViewer(val starSystem: StarSystem, val ships: Seq[Ship]) extends EntitySystemViewer {
  val entities = Seq(starSystem.star) ++ starSystem.planets

  override def toScreen(v: core.Vector): Point = {
    val rx = consoleSize.width / 2
    val ry = consoleSize.height / 2

    val r = core.Vector(v.x*rx, v.y*ry) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    Point(r.x + rx, r.y + ry)
  }
}

class PlanetViewer(val planet: Planet, val ships: Seq[Ship]) extends EntityViewer {

  val dx = consoleSize.width / 2
  val dy = consoleSize.height / 2

  def toScreen(v: core.Vector): Point = {
    val r = core.Vector(v.x*dx, v.y*dy) / 100

    implicit def toInt(d: Double): Int = Math.floor(d).toInt
    Point(r.x + dx, r.y + dy)
  }

  def viewerToScreen(p: Point): Point = (p.x + dx, -p.y + dy)

  def render(screen: Screen): Unit = {
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


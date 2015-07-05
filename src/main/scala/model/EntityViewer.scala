package model

import dynamics.Arrive
import me.mtrupkin.console.{Colors, RGB, Screen, ScreenChar}
import me.mtrupkin.core.{Points, Point, Size}
import model.space._

/**
 * Created by mtrupkin on 5/22/2015.
 */

object EntityViewer {
  def apply(entity: Entity, player: Ship): EntityViewer = {
    entity match {
      case sector: Sector => new SectorViewer(sector, player)
      case starSystem: StarSystem => new StarSystemViewer(starSystem, player)
      case planet: Planet => new PlanetViewer(planet, player)
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
  implicit def toInt(d: Double): Int = Math.floor(d).toInt
  val consoleSize: Size = Size(40, 20)

  def id: String
  def player: Ship
  def render(screen: Screen): Unit
  def target(p: Point): Option[Entity]
  def toWorld(p: Point): core.Vector = p
  def toScreen(v: core.Vector): Point = v
}

trait EntityNodeViewer extends EntityViewer {
  def entityNode: EntityNode
  def id: String = entityNode.id

  def draw(entity: Entity): ScreenChar = {
    val sc = EntityViewer.draw(entity)
    if (entity.id == player.parent) {
      sc.copy(fg = RGB(255,165,0), bg = Colors.LightGrey)
    } else sc
  }

  def render(screen: Screen): Unit = {
    entityNode.children.foreach {
      e => screen(toScreen(e.position)) = draw(e)
    }

    if (entityNode.id == player.parent) {
      screen(toScreen(player.position)) = ScreenChar('@')
      player.moveState match {
        case Arrive(Coordinate(_, target)) => screen(toScreen(target)) = ScreenChar(' ', bg = Colors.Green)
        case _ =>
      }
    }
  }

  def target(p: Point): Option[Entity] = {
    entityNode.children.find( e => toScreen(e.position) == p)
  }
}

class SectorViewer(
  val sector: Sector,
  val player: Ship) extends EntityNodeViewer  {
  def entityNode = sector
}

class StarSystemViewer(
  val starSystem: StarSystem,
  val player: Ship) extends EntityNodeViewer {

  val rx = consoleSize.width / 2
  val ry = consoleSize.height / 2

  val entityNode = starSystem

  override def toWorld(p: Point): core.Vector =  {
    val r = core.Vector(p.x - rx, ry - p.y)
    core.Vector(r.x / rx, r.y / ry) * 100
  }

  override def toScreen(v: core.Vector): Point = {
    val r = core.Vector(v.x*rx, v.y*ry) / 100

    Point(r.x + rx, ry - r.y)
  }
}

class PlanetViewer(
  val planet: Planet,
  val player: Ship) extends EntityViewer {
  def id = planet.id
  val dx = consoleSize.width / 2
  val dy = consoleSize.height / 2

  override def toScreen(v: core.Vector): Point = {
    val r = core.Vector(v.x*dx, v.y*dy) / 100
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


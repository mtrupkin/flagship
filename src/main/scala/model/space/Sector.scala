package model.space

import me.mtrupkin.console.{ScreenChar, Screen}
import me.mtrupkin.core.{Size, Points, Point}
import model.Entity

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */

class Sector(val name: String, val position: Point, val starSystems: Seq[StarSystem]) extends Entity {
  def update(elapsed: Int): Unit = {}

  def draw(p: Point): ScreenChar = {
    starSystems.find { _.position == p } match {
      case Some(ss) => '*'
      case None => '.'
    }
  }
}

object Helper {
  def pos():Point = Point(Random.nextInt(50), Random.nextInt(50))
}

object Sector {
  def apply(): Sector = {
    val starSystemCount = Random.nextInt(50)
    val starSystems = for (i <- 0 to starSystemCount) yield StarSystem()
    new Sector("Alpha", Points.Origin, starSystems)
  }
}

class StarSystem(val name: String, val position: Point, val star: Star, val planets: Seq[Planet]) extends Entity {
  def update(elapsed: Int): Unit = {}
  def draw(p: Point): ScreenChar = {
    if (p == star.position) '*' else
    planets.find { _.position == p } match {
      case Some(ps) => 'O'
      case None => '.'
    }
  }
}

object StarSystem {
  def apply(): StarSystem = {
    val planetCount = Random.nextInt(5)
    val planets = for (i <- 0 to planetCount) yield Planet()
    new StarSystem("Alpha", Helper.pos(), Star(), planets)
  }
}

class Star(val name: String, val position: Point) extends Entity {
  def update(elapsed: Int): Unit = {}
  def draw(p: Point): ScreenChar = '.'
}

object Star {
  def apply(): Star = { new Star("Alpha", Point(25, 25)) }
}

class Planet(val name: String, val position: Point) extends Entity {
  def update(elapsed: Int): Unit = {}
  def draw(p: Point): ScreenChar = '.'
}

object Planet {
  def apply(): Planet = { new Planet("Alpha", Helper.pos()) }
}


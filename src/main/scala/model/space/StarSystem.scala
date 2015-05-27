package model.space

import me.mtrupkin.console.{Colors, RGB}

import scala.util.Random

/**
 * Created by mtrupkin on 5/12/2015.
 */
class StarSystem(val id: String, val name: String, val position: core.Vector, val star: Star, val planets: Seq[Planet]) extends Entity {
  def children = Seq(star) ++ planets
}

object StarSystem {
  val typeName = "Star System"
  val typeID = "SS"
}

class Star(
  val id: String,
  val name: String,
  val position: core.Vector,
  val spectralType: Char) extends Entity

object Star {
  val typeName = "Star"
  val typeID = "ST"
}

object StarClass {
  def rndStarClass(): Char = {
    val rnd = Random.nextInt(7)
    rnd match {
      case 0 => 'O'
      case 1 => 'B'
      case 2 => 'A'
      case 3 => 'F'
      case 4 => 'G'
      case 5 => 'K'
      case 6 => 'M'
    }
  }

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
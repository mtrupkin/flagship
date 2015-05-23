package model

import me.mtrupkin.core.Point

import scala.util.Random

/**
 * Created by mtrupkin on 5/12/2015.
 */
class StarSystem(val id: String, val name: String, val position: Point, val star: Star, val planets: Seq[Planet]) extends BodySystem {
  def typeName = StarSystem.typeName
  def radius: Int = 40 // AUs
  def entities = Seq(star) ++ planets
  def update(elapsed: Int): Unit = {}
}

object StarSystem extends EntityBuilder {
  val typeName = "StarSystem"
  def apply(): StarSystem = {
    val planetCount = 2 + Random.nextInt(5)
    val planets = for (i <- 0 to planetCount) yield Planet()

    val name = for {
      i <- 0 to 5
      c = Random.nextPrintableChar()
      if c.isLetter
    } yield c

    new StarSystem(nextID(), name.mkString, Helper.pos(), Star(), planets)
  }
}

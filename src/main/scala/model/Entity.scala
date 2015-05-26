package model

import core.{Vector}
import me.mtrupkin.core.{Size, Point}

import scala.util.Random

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def parent: Entity
  def name: String
  def position: Vector

  def id: String
}

object Entity {
  def typeName(e: Entity): String = e match {
    case _: Planet => Planet.typeName
    case _: Star => Star.typeName
    case _: StarSystem => StarSystem.typeName
    case _: Sector => Sector.typeName
  }

}

trait EntityBuilder {
  var counter = 0

  def nextID(): Int = {
    counter += 1
    counter
  }

  def rndName(): String = {
    val name = for {
      i <- 0 to 5
      c = Random.nextPrintableChar()
      if c.isLetter
    } yield c
    name.mkString
  }
}

trait EntitySystem extends Entity {
  def entities: Seq[Entity]
}



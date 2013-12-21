package model

import org.flagship.console.Size

/**
 * User: mtrupkin
 * Date: 12/7/13
 */
trait TileMap {
  def apply(x: Int, y: Int): Tile
}

class TestMap extends TileMap {
  def apply(x: Int, y: Int): Tile = {
    if ((y % 3 == 0) && (x % 3 == 0)) new SimpleTile('X') else new SimpleTile('.')
  }
}

class ModuleMap {
  def apply(x: Int, y: Int): Module = ???
}

trait Module {
  def size: Size
  def apply(x: Int, y: Int): Tile = ???
}

trait Tile {
  def char: Char
}

class SimpleTile(val char: Char) extends Tile

class CargoModule extends TileMap {

  val map =
   """|XXXXXXXXXX
      |X........X
      |X.........
      |X.........
      |XXXXXX....
      |X.........
      |X.........
      |X.........
      |X.........
      |X.........
    """.stripMargin
  def apply(x: Int, y: Int): Tile = {
    val c: Char = map.charAt(y*12 + x)
    new SimpleTile(c)
  }

}
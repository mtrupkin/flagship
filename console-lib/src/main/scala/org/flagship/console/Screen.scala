package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class Screen(val size: Size){
  var fg = Color.White
  var bg = Color.Black
  var cursor = Position(0, 0)
  val buffer = Array.ofDim[ScreenCharacter](size.width, size.height)


  clear()

  def clear() = {
    for {
      i <- buffer.indices
      j <- buffer(i).indices
    } buffer(i)(j) = ScreenCharacter(' ')
  }

  def move(x: Int, y: Int) = {
    cursor = Position(x, y)
  }

  def write(c: Char) = {
    buffer(cursor.x)(cursor.y) = ScreenCharacter(c, fg, bg)
  }

  def write(x: Int, y: Int, c: Char) = {
    buffer(x)(y) = ScreenCharacter(c, fg, bg)
  }

  def write(s: String) = {
    buffer(cursor.x)(cursor.y) = ScreenCharacter(s.charAt(0), fg, bg)
  }

  def display(x: Int, y: Int, screen: Screen) = {
  }
}

object Screen {
  def apply(size: Size) = new Screen(size)
}

case class ScreenCharacter(val c: Char, val fg: Color, val bg: Color)

object ScreenCharacter {
  def apply(c: Char) = new ScreenCharacter(c, Color.White, Color.Black)
}

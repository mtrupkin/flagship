package org.flagship.console



/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class Screen(size: Size) extends Dimension {
  def width: Int = size.width
  def height: Int = size.height

  val blank = ScreenCharacter(' ')
  var fg = Color.White
  var bg = Color.Black
  var cursor = Point(0, 0)
  var offset = Point(0, 0)
  val buffer = Array.ofDim[ScreenCharacter](size.width, size.height)

  clear()

  def clear() {
    for {
      i <- 0 until size.width
      j <- 0 until size.height
    } this(i, j) = blank
  }

  def apply(x: Int, y: Int): ScreenCharacter = {
    buffer(x+offset.x)(y+offset.y)
  }

  def apply(point: Point): ScreenCharacter = {
    this(point.x, point.y)
  }

  def update(x: Int, y: Int, sc: ScreenCharacter ) {
    buffer(x+offset.x)(y+offset.y) = sc
  }

  def update(point: Point, sc: ScreenCharacter) {
    update(point.x, point.y, sc)
  }

  def foreach(f: (Point, ScreenCharacter) => Unit ) {
    for {
      i <- 0 until size.width
      j <- 0 until size.height
      if (this(i, j) != blank)
    } f(Point(i, j), this(i, j))
  }

  def move(x: Int, y: Int) {
    cursor = Point(x, y)
  }

  def write(c: Char) {
    this(cursor.x, cursor.y) = ScreenCharacter(c, fg, bg)
  }

  def write(x: Int, y: Int, c: Char) {
    this(x, y) = ScreenCharacter(c, fg, bg)
  }

  def write(s: String) {
    this(cursor.x, cursor.y) = ScreenCharacter(s.charAt(0), fg, bg)
  }

  def write(x: Int, y: Int, s: String) {
    var pos = x
    s.foreach( c => { write(pos, y, c); pos += 1 } )
  }

  def display(x: Int, y: Int, screen: Screen) {
  }
}

object Screen {
  def apply(size: Size) = new Screen(size)
}

case class ScreenCharacter(val c: Char, val fg: Color, val bg: Color)

object ScreenCharacter {
  def apply(c: Char) = new ScreenCharacter(c, Color.White, Color.Black)
}


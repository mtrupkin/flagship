package org.flagship.console


/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class Screen(val size: Size) {
  var cursorPosition: Position = null
  val visibleScreen = ofDim[ScreenCharacter](size.height, size.width)
  val backBuffer = ofDim[ScreenCharacter](size.height, size.width)

  def clear = {
    val background: ScreenCharacter = new ScreenCharacter(' ')

    visibleScreen.foreach(_ = background)
  }

  def move(x: Int, y: Int)
  def write(c: Char)
  def write(s: String)
  def foreground(c: Color)
  def background(c: Color)
  def display()
  def display(x: Int, y: Int, screen: Screen)
}

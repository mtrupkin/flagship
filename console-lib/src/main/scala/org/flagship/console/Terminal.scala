package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
trait Terminal {
  val terminalSize: Size

  val buffer = Array.ofDim[ScreenCharacter](terminalSize.width, terminalSize.height)

  var fg = Color.White
  var bg = Color.Black
  var closed = false

  def clearScreen()
  def putCharacter(x: Int, y: Int, c: Char)
  def key(): ConsoleKey

  def flush()
}

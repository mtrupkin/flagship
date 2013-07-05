package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
trait Terminal {
  val size: Size

  var fg: Color
  var bg: Color

  def display()
  def clearScreen()
  def putCharacter(x: Int, y: Int, c: Char)
}

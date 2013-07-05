package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
trait Terminal {
  def clearScreen
  def moveCursor(x: Int, y: Int)
  def putCharacter(c: Char)
  def applyForegroundColor(r: Int, g: Int, b: Int)
  def applyBackgroundColor(r: Int, g: Int, b: Int)
}

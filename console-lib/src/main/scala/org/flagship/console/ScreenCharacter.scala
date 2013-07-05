package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
case class ScreenCharacter(val c: Char, val fg: Color, val bg: Color)

object ScreenCharacter {
  def apply(c: Char) = new ScreenCharacter(c, Color.White, Color.Black)
}

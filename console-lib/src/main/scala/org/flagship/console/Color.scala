package org.flagship.console



/**
 * User: mtrupkin
 * Date: 7/5/13
 */
case class Color(val r: Int, val g: Int, val b:Int) {
  def awtColor(): java.awt.Color = {
    new java.awt.Color(r, g, b)
  }
}

object Color {
  val Black = new Color(0, 0, 0)
  val White = new Color(255, 255, 255)
}

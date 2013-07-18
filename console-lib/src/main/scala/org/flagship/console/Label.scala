package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/9/13
 */
class Label(val text: String) extends Control {
  def minSize:Dimension = Size(text.length, 1)

  def render(screen: Screen) {
    screen.write(0, 0, text)
  }
}

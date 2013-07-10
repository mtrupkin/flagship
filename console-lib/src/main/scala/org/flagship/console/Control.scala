package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
trait Control {
  def size: Size
  def render(screen: Screen)
  def width = size.width
  def height = size.height
}

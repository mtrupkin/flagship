package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
trait Control extends Dimension with Position {
  def minSize: Dimension
  def width = minSize.width
  def height = minSize.height
  def render(screen: Screen)
  def x = 0
  def y = 0
}

package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
trait Control extends Dimension {
  def minSize: Dimension
  def width = minSize.width
  def height = minSize.height
  def render(screen: Screen)
}

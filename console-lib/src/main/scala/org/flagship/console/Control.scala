package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
abstract class Control extends Dimension with Position {
  def minSize: Dimension
  def width: Int = minSize.width
  def height: Int = minSize.height
  def render(screen: Screen)
  var x:Int = 0
  var y:Int = 0
  def right:Int = x + width
  def bottom:Int = y + height
}


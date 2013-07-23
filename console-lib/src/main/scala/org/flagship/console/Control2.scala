package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
abstract class Control2( position: Position = Point.ZERO,  size: Option[Dimension] = None) extends Dimension with Position {
  def minSize: Dimension

  val width: Int = size.getOrElse(minSize).width
  val height: Int =  size.getOrElse(minSize).height

  var x: Int = position.x
  var y: Int = position.y

  var right: Int = x + width
  var bottom: Int = y + height

  def render(screen: Screen)

  def layout: Layout = Layout.NONE

  def compact() {}
  def grab() {}
  def snap() {}
}


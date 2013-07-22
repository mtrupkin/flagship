package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
abstract class Control extends Dimension with Position {
  def minSize: Dimension

  var width: Int = minSize.width
  var height: Int = minSize.height

  var x: Int = 0
  var y: Int = 0

  var right: Int = x + width
  var bottom: Int = y + height

  def render(screen: Screen)

  def layout: Layout = Layout.NONE

  def compact() {
    width = minSize.width
    height = minSize.height
  }

  def grab() {}
  def snap() {}
}


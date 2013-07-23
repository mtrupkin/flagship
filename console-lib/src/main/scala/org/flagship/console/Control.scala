package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
trait Control extends Dimension with Position {
  def minSize: Dimension = Size(1, 1)

  var width: Int = minSize.width
  var height: Int = minSize.height

  var x: Int = 0
  var y: Int = 0

  var controlLayout: Layout = Layout.NONE

  def right: Int = x + width
  def bottom: Int = y + height

  def render(screen: Screen)


  def compact() {
    width = minSize.width
    height = minSize.height
  }

  def grab(size: Dimension) {
    if (controlLayout.right.grab) {
      width = size.width - x
    }
    if (controlLayout.bottom.grab) {
      height = size.height - y
    }
  }
}


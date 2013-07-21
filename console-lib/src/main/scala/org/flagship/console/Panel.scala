package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/8/13
 */
class Panel extends Composite {
  override def addControl(control: Control) {
    super.addControl(control)
    layout()
  }

  def layout() = {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.x = lastPos.x
      c.y = lastPos.y
      lastPos = Point(c.right, 0)
    }
  }
}

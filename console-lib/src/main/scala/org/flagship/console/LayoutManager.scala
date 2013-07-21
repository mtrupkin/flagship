package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
trait LayoutManager {
  def layout(controls: List[Control])
}

class HLayout extends LayoutManager {
  def layout(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.x = lastPos.x
      c.y = lastPos.y
      lastPos = Point(c.right, 0)
    }
  }
}

class VLayout extends LayoutManager {
  def layout(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.x = lastPos.x
      c.y = lastPos.y
      lastPos = Point(0, c.bottom)
    }
  }
}

object LayoutManager {
  val HORIZONTAL: HLayout = new HLayout()
  val VERTICAL: VLayout = new VLayout()
}
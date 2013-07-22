package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
trait LayoutManager {
  def layout(controls: List[Control]) {
    controls.foreach( c => c.compact() )

    flow(controls)

    controls.foreach( c => c.grab() )
    controls.foreach( c => c.snap() )
  }

  def flow(controls: List[Control])
}

class HLayout extends LayoutManager {
  def flow(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.x = lastPos.x
      c.y = lastPos.y
      lastPos = Point(c.right, 0)
    }
  }
}

class VLayout extends LayoutManager {
  def flow(controls: List[Control]) {
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
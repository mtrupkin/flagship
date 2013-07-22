package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
trait LayoutManager {
  def layout(controls: List[Control]) {
    controls.foreach( c => c.compact() )

    flow(controls)

    controls.foreach( c => c.snap() )
    controls.reverse.foreach( c => c.snap() )

    controls.foreach( c => c.grab() )
    controls.reverse.foreach( c => c.grab() )
  }

  def flow(controls: List[Control])
}

class HLayout extends LayoutManager {
  def snap(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.x = lastPos.x
      c.y = lastPos.y
      lastPos = Point(c.right, 0)
    }
  }

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
package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
abstract class LayoutManager {

  def layout(size: Dimension, controls: List[Control]) {
    controls.foreach( c => c.compact() )

    flow(controls)

//    controls.foreach( c => c.snap() )
//    controls.reverse.foreach( c => c.snap() )
//
//    controls.foreach( c => c.grab() )
//    controls.reverse.foreach( c => c.grab() )
  }

  def flow(controls: List[Control])

  def grab(size: Dimension, controls: List[Control]): Unit
  def snap(size: Dimension, controls: List[Control]): Unit
}

class HLayout extends LayoutManager() {
  def snap(size: Dimension, controls: List[Control]) {
    var lastPos = Point(size.width, 0)

    for (c <- controls.reverse) {
      if (c.controlLayout.right.snap) {
        c.x = lastPos.x - c.width
        c.y = lastPos.y
      }
      lastPos = Point(c.x, 0)
    }
  }

  def grab(size: Dimension, controls: List[Control]): Unit = {
    var remaining: Dimension = Size(size.width, size.height)

    for(c <- controls.reverse) {
      c.grab(remaining)
      remaining = Size(remaining.width - c.width, remaining.height)
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
  def grab(size: Dimension, controls: List[Control]): Unit = {
    var remaining: Dimension = Size(size.width, size.height)

    for(c <- controls.reverse) {
      c.grab(remaining)
      remaining = Size(remaining.width, remaining.height - c.height)
    }
  }

  def snap(size: Dimension, controls: List[Control]) {
    var lastPos = Point(0, size.height)

    for (c <- controls.reverse) {
      if (c.controlLayout.bottom.snap) {
        c.x = lastPos.x - c.width
        c.y = lastPos.y - c.height
      }
      lastPos = Point(0, c.y)
    }
  }


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
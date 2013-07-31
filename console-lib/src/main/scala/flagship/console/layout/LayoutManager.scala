package flagship.console.layout

import flagship.console.control.Control
import org.flagship.console.{Size, Point}

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
abstract class LayoutManager {

  def layout(size: Size, controls: List[Control]) {
    controls.foreach( c => c.compact() )

    flow(controls)
  }

  def flow(controls: List[Control])
  def grab(size: Size, controls: List[Control]): Unit
  def snap(size: Size, controls: List[Control]): Unit
}

class HLayout extends LayoutManager {
  def flow(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.position = lastPos
      lastPos = Point(c.right, 0)
    }
  }

  def grab(size: Size, controls: List[Control]): Unit = {
    var remaining: Size = Size(size.width, size.height)

    for(c <- controls.reverse) {
      c.grab(remaining)
      remaining = Size(remaining.width - c.dimension.width, remaining.height)
    }
  }

  def snap(size: Size, controls: List[Control]) {
    var remaining: Size = Size(size.width, size.height)
    for (c <- controls.reverse) {
      c.snap(remaining)
      remaining = Size(remaining.width - c.dimension.width, remaining.height)
    }
  }
}

class VLayout extends LayoutManager {
  def flow(controls: List[Control]) {
    var lastPos = Point.ZERO
    for (c <- controls) {
      c.position = lastPos
      lastPos = Point(0, c.bottom)
    }
  }

  def grab(size: Size, controls: List[Control]): Unit = {
    var remaining: Size = Size(size.width, size.height)

    for(c <- controls.reverse) {
      c.grab(remaining)
      remaining = Size(remaining.width, remaining.height - c.dimension.height)
    }
  }

  def snap(size: Size, controls: List[Control]) {
    var remaining: Size = Size(size.width, size.height)
    for (c <- controls.reverse) {
      c.snap(remaining)
      remaining = Size(remaining.width, remaining.height - c.dimension.height)
    }
  }
}

object LayoutManager {
  val HORIZONTAL: HLayout = new HLayout()
  val VERTICAL: VLayout = new VLayout()
}
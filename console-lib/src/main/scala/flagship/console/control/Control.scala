package flagship.console.control

import org.flagship.console._
import org.flagship.console.Size
import flagship.console.terminal.Screen
import flagship.console.layout.Layout
import flagship.console.input.ConsoleKey

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
trait Control extends Update {
  def minSize: Size = Size(0, 0)

  var dimension: Size = minSize
  var position = Point.ZERO

  def right: Int = position.x + dimension.width
  def bottom: Int = position.y + dimension.height

  var controlLayout: Layout = Layout.NONE

  def render(screen: Screen)

  def keyPressed(key: ConsoleKey) {}

  def compact() {
    dimension = minSize
  }

  def grab(size: Size): Unit = {
    if (controlLayout.right.grab) {
      dimension = dimension.copy(width = size.width - position.x)
    }
    if (controlLayout.bottom.grab) {
      dimension = dimension.copy(height = size.height - position.y)
    }
  }

  def snap(size: Size) {
    if (controlLayout.right.snap) {
      position = position.copy(x = size.width - dimension.width)
    }
    if (controlLayout.bottom.snap) {
      position = position.copy(y = size.height - dimension.height)
    }
  }
}

trait Update {
  def update(elapsedTime: Int): Unit = {}
}
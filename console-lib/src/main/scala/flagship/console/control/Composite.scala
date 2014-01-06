package flagship.console.control

import org.flagship.console._
import flagship.console.terminal.Screen
import flagship.console.layout.LayoutManager

/**
 * S4i Systems Inc.
 * User: mtrupkin
 * Date: 7/8/13
 */
class Composite(val layoutManager: LayoutManager = LayoutManager.HORIZONTAL) extends Control {
  var controls = List[Control]()

  def addControl(control: Control) {
    controls = controls :+ control
  }

  def render(screen: Screen) {
    controls.foreach(c => {
      val controlScreen = Screen(c.dimension)
      c.render(controlScreen)
      screen.display(c.position, controlScreen)
    })
  }

  def layout(size: Size) {
    compact()

    grab(size)
    snap(size)
  }

  override def compact() {
    controls.foreach( c => c.compact() )
    layoutManager.flow(controls)
    super.compact()
  }

  override def snap(size: Size) {
    super.snap(size)
    layoutManager.snap(dimension, controls)
  }

  override def grab(size: Size): Unit = {
    super.grab(size)
    layoutManager.grab(dimension, controls)
  }

  override def minSize: Size = {
    var width = 0
    var height = 0

    if (controls != null) {
      controls.foreach(c => {
      if (c.right > width)
        width = c.right
      if (c.bottom > height)
        height = c.bottom} )
    }

    new Size(width, height)
  }

  override def mouseClicked(mouse: Point) {
    val m = new Point(mouse.x - position.x, mouse.y - position.y)
    for( c <- controls ) {
      if (in(c, m)) {c.mouseClicked(m)}
    }
  }

  def in(c: Control, p: Point): Boolean = {
    (c.position.x >= p.x && c.position.y >= p.y &&
      c.position.x + c.dimension.width <= p.x &&
      c.position.y + c.dimension.height <= p.y)
  }
}

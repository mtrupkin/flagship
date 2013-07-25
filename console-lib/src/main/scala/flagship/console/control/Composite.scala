package flagship.console.control

import org.flagship.console._
import flagship.console.terminal.Screen
import flagship.console.layout.LayoutManager

/**
 * S4i Systems Inc.
 * User: mtrupkin
 * Date: 7/8/13
 */
abstract class Composite(val layoutManager: LayoutManager = LayoutManager.HORIZONTAL) extends Control {
  var controls = List[Control]()

  def addControl(control: Control) {
    controls = controls :+ control
  }

  def render(screen: Screen) {
    controls.foreach(c => {
      val controlScreen = Screen(c)
      c.render(controlScreen)
      screen.display(c.x, c.y, controlScreen)
    })
  }

  def layout(size: Dimension) {
    compact()

    grab(size)
    snap(size)
  }

  override def compact() {
    controls.foreach( c => c.compact() )
    layoutManager.flow(controls)
    super.compact()
  }

  override def snap(size: Dimension) {
    super.snap(size)
    //controls.foreach(c=>c.snap(this))
    layoutManager.snap(this, controls)
  }

  override def grab(size: Dimension): Unit = {
    super.grab(size)
    //controls.foreach(c=>c.grab(this))
    layoutManager.grab(this, controls)


//    var sizeRemaining: Dimension = Size(this.width, this.height)
//    for( c <- controls.reverse ) {
//      sizeRemaining = c.grab(sizeRemaining)
//    }
//
//    sizeRemaining
  }

  override def minSize: Dimension = {
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
}

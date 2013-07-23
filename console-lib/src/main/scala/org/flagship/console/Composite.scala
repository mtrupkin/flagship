package org.flagship.console

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
    controls.foreach(c=>c.snap(this))
  }

  override def grab(size: Dimension) {
    super.grab(size)
    controls.foreach(c=>c.grab(this))
//    var sizeRemaining = Size(this.width, this.height)
//    for( c <- controls.reverse ) {
//      c.grab(sizeRemaining)
//      sizeRemaining = Size(sizeRemaining.width-c.width, sizeRemaining.height-c.height)
//    }
  }

  override def minSize: Dimension = {
    var width = 1
    var height = 1

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

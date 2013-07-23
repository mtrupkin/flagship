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

    layoutManager.layout(this, controls)
    compact()
  }


  def render(screen: Screen) {
    controls.foreach(c => {
      val controlScreen = Screen(c)
      c.render(controlScreen)
      screen.display(c.x, c.y, controlScreen)
    })
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

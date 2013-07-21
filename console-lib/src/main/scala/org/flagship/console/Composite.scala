package org.flagship.console

/**
 * S4i Systems Inc.
 * User: mtrupkin
 * Date: 7/8/13
 */
trait Composite extends Control {
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

  def minSize: Dimension = {
    var width = 1
    var height = 1

    controls.foreach(c => {
      if (c.right > width)
        width = c.right
      if (c.bottom > height)
        height = c.bottom} )


    new Size(width, height)
  }
}

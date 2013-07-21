package org.flagship.console

/**
 * S4i Systems Inc.
 * User: mtrupkin
 * Date: 7/8/13
 */
trait Composite extends Control {
  var controls = List[Control]()

  def addControl(control: Control) = {
    controls = control :: controls
  }

  def render(screen: Screen) = {

    controls.foreach(c => {
      val controlScreen = Screen(c)
      c.render(controlScreen)
      screen.display(c.x, c.y, controlScreen)
    })
  }
}

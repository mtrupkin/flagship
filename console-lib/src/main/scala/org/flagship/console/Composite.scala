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
    controls.foreach(c => c.render(screen))
  }
}

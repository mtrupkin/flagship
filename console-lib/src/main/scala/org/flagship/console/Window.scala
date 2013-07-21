package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val panel: Panel, val title: Option[String] = None) extends Composite {
  def this(size: Size, title: Option[String] = None) {
    this(size, new Panel(), title)
  }

  override def minSize: Dimension = size

  override def addControl(control: Control) {
    panel.addControl(control)
  }

  def addControl(control: Control, layout: Layout) {
    panel.addControl(control, layout)
  }

  override def render(screen: Screen) {
    panel.render(screen)
  }
}

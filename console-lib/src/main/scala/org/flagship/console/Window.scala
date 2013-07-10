package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite {
  private val panel = new Panel()
  private val border = new Border(panel)

  override def addControl(control: Control) {
    panel.addControl(control)
  }

  override def render(screen: Screen) {
    border.render(screen)
  }
}

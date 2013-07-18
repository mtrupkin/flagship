package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite {
  private val panel = new Panel()
  def minSize: Size = size

  override def addControl(control: Control) {
    panel.addControl(control)
  }

  override def render(screen: Screen) {
    panel.render(screen)
  }
}

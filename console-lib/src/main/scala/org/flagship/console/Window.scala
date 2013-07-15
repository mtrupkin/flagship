package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite with Border {
  private val panel = new Panel()
  def minSize: Size = panel.minSize

  override def addControl(control: Control) {
    panel.addControl(control)
  }

  override def render(screen: Screen) {
    panel.render(screen)
  }
}

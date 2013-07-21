package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/8/13
 */
class Panel(val layoutManager: LayoutManager) extends Composite {
  def this() { this(LayoutManager.HORIZONTAL) }

  override def addControl(control: Control) {
    super.addControl(control)
    layoutManager.layout(controls)
  }

  def addControl(control: Control, layout: Layout) {
    addControl(control)
  }
}

object Panel {
  def apply(): Panel = Panel(LayoutManager.HORIZONTAL)
  def apply(layout: LayoutManager): Panel = new Panel(layout)
}
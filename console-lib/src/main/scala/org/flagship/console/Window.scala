package org.flagship.console

import flagship.console.control.Composite

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite {
  override def minSize: Dimension = size
  def layout() {
    layout(size)
  }
}

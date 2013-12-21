package flagship.console.widget

import flagship.console.control.Composite
import org.flagship.console.{Point, Size}

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite {
  override def minSize: Size = size
  def layout() {
    layout(size)
  }
}

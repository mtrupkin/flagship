package flagship.console.widget

import org.flagship.console.{Screen, Size, Dimension}
import flagship.console.control.Control

/**
 * User: mtrupkin
 * Date: 7/9/13
 */
class Label(val text: String) extends Control {

  override def minSize: Dimension =  Size(text.length, 1)

  def render(screen: Screen) {
    screen.write(0, 0, text)
  }
}

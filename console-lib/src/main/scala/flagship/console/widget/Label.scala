package flagship.console.widget

import org.flagship.console.{Size, Point}
import flagship.console.control.{Control}
import flagship.console.terminal.Screen

/**
 * User: mtrupkin
 * Date: 7/9/13
 */
class Label(label: String, text: => String, length: Int) extends Control {

  override def minSize: Size =  Size(length + label.length, 1)

  def render(screen: Screen) {
    screen.write(0, 0, label + text)
  }
}

package flagship.console.terminal

import flagship.console.input.ConsoleKey
import flagship.console.widget.Window


/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class GUIConsole(val terminal: Terminal, val window: Window) {
  val framesPerSecond = 23
  val refreshRate = (1f / framesPerSecond) * 1000
  val screen = Screen(terminal.terminalSize)

  def completed(): Boolean = terminal.closed

  var consoleKey: Option[ConsoleKey] = None

  def render() {
    window.render(screen)
    terminal.flush(screen)
  }

  def processInput() {
    if (terminal.key != consoleKey) {
      consoleKey = terminal.key
      if (consoleKey != None) {
        println("key pressed: " + consoleKey.get.keyValue)
      } else {
        println("key released")
      }
    }
  }

  def doEventLoop() {
    var lastUpdateTime = System.currentTimeMillis()

    while (!completed()) {
      val currentTime = System.currentTimeMillis()
      val elapsedTime = currentTime - lastUpdateTime


      if (elapsedTime > refreshRate) {
        lastUpdateTime = currentTime
        processInput
        render
      }
    }
  }
}

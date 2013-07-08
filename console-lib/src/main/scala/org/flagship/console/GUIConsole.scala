package org.flagship.console


/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class GUIConsole(val terminal: Terminal) {
  val framesPerSecond = 23
  val refreshRate = (1f / framesPerSecond) * 1000

  val screen = Screen(terminal.terminalSize)

  var controls = List[Control]()

  def completed(): Boolean = { terminal.closed }

  var consoleKey: ConsoleKey = null

  def flush() = {
    controls.foreach(c => c.render(screen))

    val buffer = screen.buffer
    for {
      i <- buffer.indices
      j <- buffer(i).indices
    } terminal.putCharacter(i, j, buffer(i)(j).c)

    terminal.flush()
    if (terminal.key() != consoleKey) {
      consoleKey = terminal.key()
      if (consoleKey != null) {
        println("key pressed: " + consoleKey.keyValue)
      } else {
        println("key released")
      }
    }
  }

  def showWindow(window: Window) = {
    controls = window :: controls
  }

  def doEventLoop() = {

    var lastUpdateTime = System.currentTimeMillis()

    while (!completed()) {
      val currentTime = System.currentTimeMillis()
      val elapsedTime = currentTime - lastUpdateTime

      if (elapsedTime > refreshRate) {
        lastUpdateTime = currentTime
        flush
      }
    }
  }
}

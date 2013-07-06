package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class GUIConsole(val terminal: Terminal) {
  val framesPerSecond = 24

  val refreshRate = (1f / framesPerSecond) * 1000

  val screen = Screen(terminal.terminalSize)

  def completed() = { terminal.closed }

  def flush() = {
    val buffer = screen.buffer
    for {
      i <- buffer.indices
      j <- buffer(i).indices
    } terminal.putCharacter(i, j, buffer(i)(j).c)

    terminal.flush()
  }

  def showWindow(window: com.googlecode.lanterna.gui.Window) = {
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

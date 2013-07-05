package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class GUIConsole(val terminal: Terminal) {
  val screen = Screen(terminal.size)

  def refresh(): Unit = {
    val buffer = screen.buffer
    for {
      i <- buffer.indices
      j <- buffer(i).indices
    } terminal.putCharacter(i, j, buffer(i)(j).c)

  }

  def showWindow(window: com.googlecode.lanterna.gui.Window): Unit = {
    terminal.display()
  }

}

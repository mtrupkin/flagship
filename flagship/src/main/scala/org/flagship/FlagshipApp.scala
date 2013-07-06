package org.flagship

import org.flagship.console.{GUIConsole, SwingTerminal}
import com.googlecode.lanterna.gui.{Border, Window}
import com.googlecode.lanterna.gui.component.Panel
import com.googlecode.lanterna.gui.listener.WindowAdapter
import com.googlecode.lanterna.input.Key


class FlagshipWindow extends Window("Flagship") {
  val horisontalPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
  val leftPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
  val middlePanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
  val rightPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);

  horisontalPanel.addComponent(leftPanel);
  horisontalPanel.addComponent(middlePanel);
  horisontalPanel.addComponent(rightPanel);

  addComponent(horisontalPanel);
}

object FlagshipApp extends App {

  val term = new SwingTerminal()
  val gui = new GUIConsole(term)

  val window = new FlagshipWindow()

  window.addWindowListener(new WindowAdapter {
    override def onUnhandledKeyboardInteraction(window: Window, key: Key) {
      if (key.getKind == Key.Kind.Escape) {
        window.close()
      }
    }
  })

  gui.showWindow(window)
  gui.doEventLoop()

}
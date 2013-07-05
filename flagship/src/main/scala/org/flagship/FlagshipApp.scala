package org.flagship

import com.googlecode.lanterna.gui.component.Panel
import com.googlecode.lanterna.gui.listener.WindowAdapter
import com.googlecode.lanterna.gui.{GUIScreen, Border, Window}
import com.googlecode.lanterna.input.Key
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.TerminalFacade

class  FlagshipWindow extends Window("Flagship") {
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

  println( "Flagship")

  val term = TerminalFacade.createSwingTerminal()
  val screen = new Screen(term)
  val gui = new GUIScreen(screen)
  val window = new FlagshipWindow()

  window.addWindowListener(new WindowAdapter {
    override def onUnhandledKeyboardInteraction(window: Window, key: Key) {
      if (key.getKind == Key.Kind.Escape) {
        window.close()
        screen.stopScreen()
      }
    }
  })

  screen.startScreen()
  gui.showWindow(window)

}
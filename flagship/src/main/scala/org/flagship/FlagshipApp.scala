package org.flagship

import org.flagship.console._
import org.flagship.console.Size
import scala.Some


class FlagshipWindowOld extends com.googlecode.lanterna.gui.Window("Flagship") {
  import com.googlecode.lanterna.gui.component.Panel
  import com.googlecode.lanterna.gui.Border
  val horisontalPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
  val leftPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
  val middlePanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
  val rightPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);

  horisontalPanel.addComponent(leftPanel);
  horisontalPanel.addComponent(middlePanel);
  horisontalPanel.addComponent(rightPanel);

  addComponent(horisontalPanel);
}

class FlagshipWindow(size: Size) extends Window(size, Some("Flagship Window")) {
  val horizontalPanel = new Panel()
  val leftPanel = new Panel()
  val middlePanel = new Panel()
  val rightPanel = new Panel()

  val label = new Label("Test Label") with Border
  middlePanel.addControl(label)

  horizontalPanel.addControl(leftPanel);
  horizontalPanel.addControl(middlePanel);
  horizontalPanel.addControl(rightPanel);

  addControl(horizontalPanel);
}

object FlagshipApp extends App {
  val size = Size(100, 40)
  val term = new SwingTerminal(size, "Flagship Terminal")
  val window = new FlagshipWindow(size)
  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
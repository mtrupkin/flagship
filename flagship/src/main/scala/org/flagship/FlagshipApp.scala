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
  val horizontalPanel = new Panel(LayoutManager.HORIZONTAL) with Border
  val leftPanel = new Panel() with Border
  val rightPanel = new Panel() with Border
  val topRightPanel = new Panel() with Border
  val bottomRightPanel = new Panel() with Border

  val leftLabel = new Label("Left")
  leftPanel.addControl(leftLabel)

  val rightLabel = new Label("Right")
  rightPanel.addControl(rightLabel)

  horizontalPanel.addControl(leftPanel)
  horizontalPanel.addControl(rightPanel)
  //rightPanel.addControl(topRightPanel)
  //rightPanel.addControl(bottomRightPanel)
  Layout().copy(right = LayoutData(false, true))
  addControl(horizontalPanel, Layout().copy(right = LayoutData(false, true)))
}

object FlagshipApp extends App {
  val size = Size(100, 40)
  val term = new SwingTerminal(Size(102, 42), "Flagship Terminal")
  val window = new FlagshipWindow(Size(100, 40)) with Border
  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
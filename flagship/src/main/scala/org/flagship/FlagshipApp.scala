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
  val horizontalPanel = new Composite(LayoutManager.HORIZONTAL) with Border
  val leftPanel = new Composite() with Border
  val rightPanel = new Composite() with Border
  val topRightPanel = new Composite() with Border
  val bottomRightPanel = new Composite() with Border
  import LayoutData._

  val leftLabel = new Label("Left")
  leftPanel.controlLayout = Layout(right = GRAB, bottom = NONE)
  leftPanel.addControl(leftLabel)

  val rightLabel = new Label("Right")
  rightPanel.addControl(rightLabel)
  rightPanel.controlLayout = Layout(right = SNAP, bottom = GRAB)

  horizontalPanel.addControl(leftPanel)
  horizontalPanel.addControl(rightPanel)
  //rightPanel.addControl(topRightPanel)
  //rightPanel.addControl(bottomRightPanel)

  horizontalPanel.controlLayout = Layout(right = GRAB, bottom = GRAB)

  addControl(horizontalPanel)

  layout()
}

object FlagshipApp extends App {
  val size = Size(100, 40)
  val term = new SwingTerminal(Size(102, 42), "Flagship Terminal")
  val window = new FlagshipWindow(Size(100, 40)) with Border

  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
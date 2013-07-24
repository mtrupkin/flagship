package org.flagship

import org.flagship.console._
import org.flagship.console.Size
import scala.Some
import flagship.console.widget.{Border, Label}
import flagship.console.control.Composite


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
  val rightPanel = new Composite(LayoutManager.VERTICAL) with Border
  val topRightPanel = new Composite() with Border
  val bottomRightPanel = new Composite() with Border
  import LayoutData._

  val leftLabel = new Label("Left") with Border
  val leftLabel2 = new Label("Left2") with Border
  val leftInnerPanel = new Composite() with Border
  val rightLabel = new Label("Right") with Border

  topRightPanel.controlLayout = Layout(bottom = GRAB)
  bottomRightPanel.controlLayout = Layout(bottom = SNAP, right = GRAB)
  leftLabel.controlLayout = Layout(bottom = GRAB)
  leftLabel2.controlLayout = Layout(right = SNAP, bottom = SNAP)
  leftInnerPanel.controlLayout = Layout(right = SNAP, bottom = SNAP)
  leftPanel.controlLayout = Layout(right = GRAB, bottom = GRAB)
  rightPanel.controlLayout = Layout(right = SNAP, bottom = GRAB)
  //rightLabel.controlLayout = Layout(right = SNAP, bottom = GRAB)

  leftPanel.addControl(leftLabel)
  leftPanel.addControl(leftLabel2)
  leftPanel.addControl(leftInnerPanel)

  rightPanel.addControl(rightLabel)
  rightPanel.addControl(topRightPanel)
  rightPanel.addControl(bottomRightPanel)



  horizontalPanel.addControl(leftPanel)
  horizontalPanel.addControl(rightPanel)


  horizontalPanel.controlLayout = Layout(right = GRAB, bottom = GRAB)

  //addControl(leftLabel)
  //addControl(rightLabel)
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
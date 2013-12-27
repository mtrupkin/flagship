package org.flagship

import org.flagship.console._
import org.flagship.console.Size
import scala.Some
import flagship.console.widget.{Window, Border, Label}
import flagship.console.control.Composite
import flagship.console.terminal.{GUIConsole, SwingTerminal}
import flagship.console.layout.{LayoutManager, LayoutData, Layout}


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

class TestWindow(size: Size) extends Window(size, Some("Test Window")) {
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

class FlagshipWindow(size: Size) extends Window(size, Some("Flagship Window")) {
  val windowPanel = new Composite()
  val mainPanel = new Composite() with Border
  val detailPanel = new Composite(LayoutManager.VERTICAL) with Border
  val fleetListPanel = new Composite() with Border
  val statusPanel = new Composite() with Border
  val weaponPanel = new Composite() with Border
  val targetPanel = new Composite() with Border

  import LayoutData._

  windowPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  mainPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  detailPanel.controlLayout = Layout(bottom = GRAB, right = SNAP)

  statusPanel.controlLayout = Layout(right = GRAB)
  weaponPanel.controlLayout = Layout(right = GRAB)
  targetPanel.controlLayout = Layout(right = GRAB)

  detailPanel.addControl(statusPanel)
  detailPanel.addControl(weaponPanel)
  detailPanel.addControl(targetPanel)

  windowPanel.addControl(mainPanel)
  windowPanel.addControl(detailPanel)

  addControl(windowPanel)
  layout()
}

object FlagshipApp extends App {
  val size = Size(100, 40)
  val term = new SwingTerminal(Size(100, 40), "Flagship Terminal")
  val window = new FlagshipWindow(Size(100, 40))

  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
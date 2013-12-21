package org.flagship

/**
 * User: mtrupkin
 * Date: 11/29/13
 */

import org.flagship.console._
import org.flagship.console.Size
import org.flagship.console.Size
import scala.Some
import flagship.console.widget.{Window, Border, Label}
import flagship.console.control.Composite
import flagship.console.terminal.{GUIConsole, SwingTerminal}
import flagship.console.layout.{LayoutManager, LayoutData, Layout}
import model.{Player, CargoModule, TestMap}
import view.MapView
import flagship.console.input.ConsoleKey


class TestWindow(size: Size) extends Window(size, Some("Test Window")) {
/*
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
*/
}

class FlagshipWindow(size: Size) extends Window(size, Some("Flagship Window")) {
  var time = 0

  val player = new Player

  val windowPanel = new Composite()
  val mainPanel = new Window(Size(80, 40)) with Border
  val mapPanel = new Composite(LayoutManager.VERTICAL) with Border
  val insideMapPanel = new Composite() with Border
  val outsideMapPanel = new Composite() with Border
  val detailPanel = new Composite(LayoutManager.VERTICAL) with Border
  val timeWidget = new Label("time: ", time.toString, 6)

  import LayoutData._

  windowPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  //mainPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  mapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  insideMapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  outsideMapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  detailPanel.controlLayout = Layout(bottom = GRAB, right = SNAP)

  mainPanel.addControl(new MapView(new TestMap, player.position) with Border)

  mapPanel.addControl(insideMapPanel)
  mapPanel.addControl(outsideMapPanel)

  detailPanel.addControl(timeWidget)
  windowPanel.addControl(mainPanel)
  windowPanel.addControl(mapPanel)
  windowPanel.addControl(detailPanel)


  addControl(windowPanel)
  layout()

  override def update(elapsedTime: Int) {
    time += elapsedTime
  }

  override def keyPressed(key: ConsoleKey) {
    import scala.swing.event.Key._
    val k = key.keyValue
     k match {
      case W => player.up
      case A => player.left
      case S => player.down
      case D => player.right
      case _ =>
    }
  }
}

object RamaApp extends App {
  val size = Size(100, 47)
  val term = new SwingTerminal(Size(100, 42), "Rama Terminal")
  val window = new FlagshipWindow(Size(100, 42))

  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
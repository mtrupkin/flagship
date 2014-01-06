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
import flagship.console.control.{Control, Composite}
import flagship.console.terminal.{Screen, GUIConsole, SwingTerminal}
import flagship.console.layout.{LayoutManager, LayoutData, Layout}
import model._
import view.MapView
import flagship.console.input.ConsoleKey
import flagship.console.layout.LayoutData._
import scala.Some
import org.flagship.console.Size
import scala.Some
import org.flagship.console.Size


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
class ShipControlsPanel extends Composite(LayoutManager.VERTICAL) with Border{
  val spacer = new Label("", "", 1)
  val cursorTarget = new Label("    Frigate-1 [2.00, 76.00] ", "", 6)
  val targetNone   = new Label("         None [0.00, 0.00] ", "", 6)

  val target1      = new Label("    Frigate-1 [2.00, 76.00] ", "", 6)
  val target2      = new Label("Dreadnaught-1 [2.00, 76.00] ", "", 6)
  val target3      = new Label("   Starbase-7 [722.00, 7.00]", "", 6)

  val navigation   = new Label("Destination-", "", 6)
  val shields      = new Label("    Shields: 53", "", 6)
  val weapons      = new Label("Weapons - [Lock] [Fire]", "", 6)
  val weap1        = new Label("Torpedoes-1: ", "", 6)
  val weap2        = new Label("Phasers-3: ", "", 6)
  val weap3        = new Label("Phasers-1: ", "", 6)

  addControl(cursorTarget)
  addControl(spacer)
  addControl(navigation)
  addControl(target3)
  addControl(spacer)
  addControl(shields)
  addControl(spacer)
  addControl(weapons)
  addControl(weap1)
  addControl(target1)
  addControl(weap2)
  addControl(target2)
  addControl(weap3)
  addControl(targetNone)
}

class ShipPanel2 extends Composite(LayoutManager.VERTICAL) with Border{
  val spacer = new Label("", "", 1)
  val targetNone   = "         None"

  val cursorTarget = new Label("    Frigate-1 [2.00, 76.00]", "", 2)
  val target0      = "    Frigate-1"
  val target1      = "    Frigate-1"
  val target2      = "Dreadnaught-1"
  val target3      = "   Starbase-7"

  val navigation   = new Label("Destination: ", target3, 13)
  val shields      = new Label("    Shields: 53", "", 6)

  addControl(cursorTarget)
  addControl(navigation)
  addControl(shields)

 }
class WeaponControl(weapon: Weapon, length: Int) extends Control {
  override def minSize: Size =  Size(length + weapon.name.length + 2, 1)

  def render(screen: Screen) {
    screen.write(0, 0, weapon.name + ": " + weapon.target.name)
  }

  override def mouseClicked(mouse: Point) {
    println("mouse clicked")
  }
}

class WeaponsPanel extends Composite(LayoutManager.VERTICAL) with Border {
  controlLayout = Layout(right = GRAB)

  val target1      = new BaseEntity("Frigate-1")
  val target2      = new BaseEntity("Dreadnaught-1")
  val target3      = new BaseEntity("Starbase-7")

  val weap1 = new Weapon("Torpedo")
  val weap2 = new Weapon(" Phaser")
  weap2.target = target1
  val weap3 = new Weapon(" Phaser")
  weap3.target = target2

  val weapons      = new Label("Weapons - [Fire]", "", 6)
  val weaponControl1 = new WeaponControl(weap1, 13)
  val weaponControl2 = new WeaponControl(weap2, 13)
  val weaponControl3 = new WeaponControl(weap3, 13)

  addControl(weapons)
  addControl(weaponControl1)
  addControl(weaponControl2)
  addControl(weaponControl3)
}

class FlagshipWindow(size: Size) extends Window(size, Some("Flagship Window")) {
  var time = 0

  val player = new Player

  val windowPanel = new Composite()
  val mainPanel = new Window(Size(40, 40)) with Border
  val mapPanel = new Composite(LayoutManager.VERTICAL) with Border
  val insideMapPanel = new Composite() with Border
  val outsideMapPanel = new Composite() with Border
  val detailPanel = new Composite(LayoutManager.VERTICAL) with Border
  val shipPanel = new ShipPanel2
  val weaponsPanel = new WeaponsPanel

  import LayoutData._

  windowPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  //mainPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  mapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  insideMapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  outsideMapPanel.controlLayout = Layout(bottom = GRAB, right = GRAB)
  detailPanel.controlLayout = Layout(bottom = GRAB, right = SNAP)
  shipPanel.controlLayout = Layout(right = GRAB)
  weaponsPanel.controlLayout = Layout(right = GRAB) // TODO: fix right grab for vertical layout

  mainPanel.addControl(new MapView(new TestMap, player.position) with Border)

  mapPanel.addControl(insideMapPanel)
  mapPanel.addControl(outsideMapPanel)

  detailPanel.addControl(shipPanel)
  detailPanel.addControl(weaponsPanel)

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
  val size = Size(120, 42)
  val term = new SwingTerminal(size, "Rama Terminal")
  val window = new FlagshipWindow(size)

  val gui = new GUIConsole(term, window)

  gui.doEventLoop()
}
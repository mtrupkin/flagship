package controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.UniverseTracker
import model.space.Universe

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: FlagshipController =>
  class GameController(val world: Universe with UniverseTracker) extends ControllerState {
    val name = "Game"

    @FXML var label1: Label = _
    @FXML var label2: Label = _
    @FXML var targetNameText: Label = _
    @FXML var targetActionText: Label = _
    @FXML var targetDescriptionText: Label = _
    @FXML var targetPositionText: Label = _
    @FXML var consolePane: Pane = _
    @FXML var consolePane2: Pane = _
    @FXML var rootPane: Pane = _

    var console: ConsoleFx = _
    var console2: ConsoleFx = _
    var screen: Screen = _
    var screen2: Screen = _

    def initialize(): Unit = {
      val consoleSize1 = Size(80, 40)
      val consoleSize2 = Size(60, 30)
      console = new ConsoleFx(consoleSize1)
      console.setStyle("-fx-border-color: white")

      console2 = new ConsoleFx(consoleSize2)
      console2.setStyle("-fx-border-color: white")

      new sfxl.Pane(rootPane) {
        filterEvent(sfxi.KeyEvent.Any) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }

      new sfxl.Pane(console) {
        onMouseClicked = (e: sfxi.MouseEvent) => handleMouseClicked(e)
        onMouseMoved = (e: sfxi.MouseEvent) => handleMouseMove(e)
        onMouseExited = (e: sfxi.MouseEvent) => handleMouseExit(e)
      }

      screen = Screen(consoleSize1)
      screen2 = Screen(consoleSize2)
      consolePane.getChildren.clear()
      consolePane.getChildren.add(console)
      consolePane.setFocusTraversable(true)

      consolePane2.getChildren.clear()
      consolePane2.getChildren.add(console2)
      consolePane2.setFocusTraversable(true)

      timer.start()
    }

    override def update(elapsed: Int): Unit = {
      // TODO: update and render at different rates

      world.update(elapsed)
      world.render(screen)
      world.render2(screen2)

      console.draw(screen)
      console2.draw(screen2)

    }

    implicit def itos(int: Int): String = int.toString

    implicit def pointToString(p: Point): String = {
      s"[${p.x}, ${p.y}]"
    }

    def handleMouseClicked(mouseEvent: sfxi.MouseEvent): Unit = {
      for( s <- mouseToPoint(mouseEvent)) {

      }
    }

    def updateMouseInfo(w: Point): Unit = {
      targetPositionText.setText(s"${w.x}:${w.y}")
    }

    def updateActionText(name: String, description: String): Unit = {
      targetActionText.setText(name)
      targetDescriptionText.setText(description)
    }

    def handleMouseMove(mouseEvent: sfxi.MouseEvent): Unit = {
      for( s <- mouseToPoint(mouseEvent)) {
        world.target = s
        updateMouseInfo(s)
      }
    }

    def handleMouseExit(mouseEvent: sfxi.MouseEvent): Unit = {
      targetNameText.setText("")
      targetActionText.setText("")
      targetDescriptionText.setText("")
      targetPositionText.setText("")
    }

    def mouseToPoint(mouseEvent: sfxi.MouseEvent): Option[Point] = console.toScreen(mouseEvent.x, mouseEvent.y)

    def handleKeyPressed(event: sfxi.KeyEvent): Unit = {
      import me.mtrupkin.console.Key._
      val key = keyCodeToConsoleKey(event)
      key match {
        case ConsoleKey(Q, Modifiers.Control) => {
          changeState(new IntroController)
        }
        case ConsoleKey(X, Modifiers.Control) => exit()
        case ConsoleKey(k, _) => k match {
          case Space =>
          case Esc => changeState(new IntroController)
          case A =>
          case _ =>
        }
        case _ =>
      }
    }
  }

  def keyCodeToConsoleKey(event: sfxi.KeyEvent): ConsoleKey = {
    val modifier = Modifier(event.shiftDown, event.controlDown, event.altDown)
    val jfxName = event.code.name
    val tryKey = Try {
      Key.withName(jfxName)
    }

    tryKey match {
      case Success(key) => ConsoleKey(key, modifier)
      case Failure(ex) => ConsoleKey(Key.Undefined, modifier)
    }
  }
}


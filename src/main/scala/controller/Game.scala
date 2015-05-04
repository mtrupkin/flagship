package controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.WorldTracker

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: FlagshipController =>
  class GameController(val world: WorldTracker) extends ControllerState {
    val name = "Game"

    @FXML var label1: Label = _
    @FXML var label2: Label = _
    @FXML var targetNameText: Label = _
    @FXML var targetActionText: Label = _
    @FXML var targetDescriptionText: Label = _
    @FXML var targetPositionText: Label = _
    @FXML var consolePane: Pane = _
    @FXML var rootPane: Pane = _

    var console: ConsoleFx = _
    var screen: Screen = _

    def initialize(): Unit = {
      val consoleSize = Size(80, 40)
      console = new ConsoleFx(consoleSize)
      console.setStyle("-fx-border-color: white")

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

      screen = Screen(consoleSize)
      consolePane.getChildren.clear()
      consolePane.getChildren.add(console)
      consolePane.setFocusTraversable(true)

      timer.start()
    }

    override def update(elapsed: Int): Unit = {
      // TODO: update and render at different rates
      world.update(elapsed)
      world.render(screen)

      world.renderPath(screen, world.workerPrototype, world.target)

      console.draw(screen)

      label1.setText(s"${world.home.store}")
    }

    implicit def itos(int: Int): String = int.toString

    implicit def pointToString(p: Point): String = {
      s"[${p.x}, ${p.y}]"
    }

    def handleMouseClicked(mouseEvent: sfxi.MouseEvent): Unit = {
      for( s <- mouseToPoint(mouseEvent)) {
        world.addSite(s)
      }
    }

    def updateMouseInfo(w: Point): Unit = {
      targetPositionText.setText(s"${w.x}:${w.y}")
      label2.setText(s"${world(w).cost}")

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


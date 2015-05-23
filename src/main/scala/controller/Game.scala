package controller

import javafx.fxml.{FXMLLoader, FXML}
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import component.EntityController
import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.{EntityControl, UniverseTracker, Universe}

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: MainController =>
  class GameController(val world: Universe with UniverseTracker) extends ControllerState {
    val name = "main-game"

    override def root: Parent = {
      val is = getClass.getResourceAsStream(templateName)
      val loader = new FXMLLoader()
      loader.setLocation(getClass.getResource(templateName))
      loader.setController(this)
      loader.load[Parent](is)
    }

    @FXML var rootPane: Pane = _
    @FXML var entity1: Pane = _
    @FXML var entity1Controller: EntityController = _
    @FXML var entity2: Pane = _
    @FXML var entity2Controller: EntityController = _

    def initialize(): Unit = {
      new sfxl.Pane(rootPane) {
        filterEvent(sfxi.KeyEvent.Any) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }

      entity1Controller.setEntity(world.entitySystem1)
      entity2Controller.setEntity(world.entitySystem2)

      entity1Controller.entityProperty.onChange({
        entity2Controller.setEntity(EntityControl(entity1Controller.entityProperty.value))
      })

      timer.start()
    }

    override def update(elapsed: Int): Unit = {
      // TODO: update and render at different rates
      world.update(elapsed)

      entity1Controller.update(elapsed)
      entity2Controller.update(elapsed)
    }

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

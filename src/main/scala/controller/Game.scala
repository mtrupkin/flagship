package controller

import javafx.fxml.{FXMLLoader, FXML}
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import component.EntityController
import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.space.Universe
import model.{SectorViewer, EntityViewer}

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: MainController =>
  class GameController(val world: Universe) extends ControllerState {
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
        filterEvent(sfxi.KeyEvent.KeyReleased) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }

      val entityViewer1: EntityViewer = new SectorViewer(world.sectors(0))
      val entityViewer2: EntityViewer = EntityViewer(entityViewer1, world.sectors(0).starSystems(0))

      entity1Controller.setEntityViewer(entityViewer1)
      entity2Controller.setEntityViewer(entityViewer2)

      entity1Controller.entityHighlighted.onChange({
        val entityViewer = EntityViewer(entity1Controller.entityViewer, entity1Controller.entityHighlighted.value)
        entity2Controller.setEntityViewer(entityViewer)
      })

      entity1Controller.entitySelected.onChange({
        val entityViewer = EntityViewer(entity1Controller.entityViewer, entity1Controller.entityHighlighted.value)
        entity1Controller.setEntityViewer(entityViewer)
      })

      entity2Controller.entitySelected.onChange({
        val entityViewer = EntityViewer(entity2Controller.entityViewer, entity2Controller.entitySelected.value)
        entity1Controller.setEntityViewer(entityViewer.parent)
        entity2Controller.setEntityViewer(entityViewer)
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
          case Esc => {
            entity2Controller.setEntityViewer(entity1Controller.entityViewer)
            entity1Controller.setEntityViewer(entity1Controller.entityViewer.parent)
          }
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

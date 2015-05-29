package controller

import javafx.fxml.{FXMLLoader, FXML}
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import component.EntityController
import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.space.{Entity, Universe}
import model.{SectorViewer, EntityViewer}

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: MainController =>
  class GameController(var world: Universe) extends ControllerState {
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

    var entityID1: String = _
    var entityID2: String = _

    def initialize(): Unit = {
      new sfxl.Pane(rootPane) {
        filterEvent(sfxi.KeyEvent.KeyReleased) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }


      entityID1 = world.sectors(0).id
      entityID2 = world.sectors(0).starSystems(0).id


      entity1Controller.entityHighlighted.onChange({
        entityID2 = entity1Controller.entityHighlighted.value.id
      })

      entity1Controller.entitySelected.onChange({
        val id = entity1Controller.entitySelected.value.id
        if (!world.locate(id).children.isEmpty) {
          entityID1 = id
        }

      })

      entity2Controller.entitySelected.onChange({
        val entityID = entity2Controller.entitySelected.value.id
        val parentID = world.locate(entityID).parent

        entity1Controller.setEntity(world.locate(parentID))
        entity2Controller.setEntity(world.locate(entityID))
      })

      timer.start()
    }

    override def update(elapsed: Int): Unit = {
      // TODO: update and render at different rates
      world = world.update(elapsed)

      entity1Controller.setEntity(world.locate(entityID1))
      entity2Controller.setEntity(world.locate(entityID2))
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
            if (world.locate(entityID1).parent != Entity.RootID) {
              entityID2 = entityID1
              entityID1 = world.locate(entityID1).parent
            }
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

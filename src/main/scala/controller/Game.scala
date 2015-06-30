package controller

import javafx.fxml.{FXMLLoader, FXML}
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.Pane

import component.{EntityReadoutController, EntityController}
import me.mtrupkin.console._
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Point, Size}
import model.space.{EntityNode, EntityLeaf, Entity, Universe}
import model.{SectorViewer, EntityViewer}

import scala.util.{Failure, Success, Try}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 12/15/2014.
 */
trait Game { self: MainController =>
  class GameController(var universe: Universe) extends ControllerState {
    val name = "main-game"

    override def root: Parent = {
      val is = getClass.getResourceAsStream(templateName)
      val loader = new FXMLLoader()
      loader.setLocation(getClass.getResource(templateName))
      loader.setController(this)
      loader.load[Parent](is)
    }

    @FXML var rootPane: Pane = _
    @FXML var entityView1: Pane = _
    @FXML var entityView1Controller: EntityController = _
    @FXML var entityView2: Pane = _
    @FXML var entityView2Controller: EntityController = _
    @FXML var targetReadoutController: EntityReadoutController = _

    var entity1: Entity = _
    var entity2: Entity = _

    def initialize(): Unit = {
      new sfxl.Pane(rootPane) {
        filterEvent(sfxi.KeyEvent.KeyReleased) {
          (event: sfxi.KeyEvent) => handleKeyPressed(event)
        }
      }

      entity1 = universe.sectors(0)
      entity2 = universe.sectors(0).starSystems(0)

      entityView1Controller.entityPrimarySelected.onChange({
        entity2 = entityView1Controller.entityPrimarySelected.value
      })

      entityView2Controller.entityPrimarySelected.onChange({
        val newEntity = entityView2Controller.entityPrimarySelected.value
        entity1 = entity2
        entity2 = newEntity
      })

      entityView1Controller.entityHighlighted.onChange({
        val entity = entityView1Controller.entityHighlighted.value
        targetReadoutController.update(entity)
      })

      entityView2Controller.entityHighlighted.onChange({
        val entity = entityView2Controller.entityHighlighted.value
        targetReadoutController.update(entity)
      })

      timer.start()
    }

    override def update(elapsed: Int): Unit = {
      // TODO: update and render at different rates
      universe = universe.update(elapsed)

      entityView1Controller.setEntity(universe.locate(entity1.id), universe.ships)
      entityView2Controller.setEntity(universe.locate(entity2.id), universe.ships)
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
            entity1 match {
              case branch:EntityNode  => {
                val parent = universe.locate(branch.parent)
                parent match {
                  case _: EntityNode | _: EntityLeaf => {
                    entity2 = entity1
                    entity1 = universe.locate(branch.parent)
                  }
                  case _ =>
                }
              }
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

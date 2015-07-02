package component

import javafx.fxml.FXML
import javafx.scene.layout.Pane

import me.mtrupkin.console.Screen
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Size, Point}
import model.EntityViewer
import model.space.{Ship, Entity}

import scalafx.Includes._
import scalafx.beans.property.{ObjectProperty, Property}
import scalafx.scene.input.MouseButton
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 5/22/2015.
 */
class EntityController {
  @FXML var consoleParent: Pane = _

  val entityHighlighted = new ObjectProperty[Entity]
  val entityPrimarySelected = new ObjectProperty[Entity]
  val entitySecondarySelected = new ObjectProperty[Entity]

  var console: ConsoleFx = _
  var screen: Screen = _
  var entityViewer: EntityViewer = _

  def initialize(): Unit = {
    val consoleSize = Size(40, 20)
    console = new ConsoleFx(consoleSize, fontSize = 23)
    console.setStyle("-fx-border-color: white")
    screen = Screen(consoleSize)

    consoleParent.onMouseMoved = (e: sfxi.MouseEvent) => handleMouseMove(e)
    consoleParent.onMouseClicked = (e: sfxi.MouseEvent) => handleMouseClicked(e)
    consoleParent.onMouseExited = (e: sfxi.MouseEvent) => handleMouseExit(e)

    consoleParent.getChildren.add(console)
    consoleParent.setFocusTraversable(true)
  }

  def setEntity(entity: Entity, ships: Seq[Ship]) {
    screen.clear()
    this.entityViewer = EntityViewer(entity, ships)

    entityViewer.render(screen)

    console.draw(screen)
  }

  def handleMouseMove(mouseEvent: sfxi.MouseEvent): Unit = {
    for( s <- mouseToPoint(mouseEvent)) {
      updateMouseInfo(s)
    }
  }

  def handleMouseClicked(mouseEvent: sfxi.MouseEvent): Unit = {
    for {
      s <- mouseToPoint(mouseEvent)
      target <- entityViewer.target(s)
    } {
      mouseEvent.button match {
        case MouseButton.PRIMARY => entityPrimarySelected.update(target)
        case MouseButton.SECONDARY => entitySecondarySelected.update(target)
      }
    }
  }

  def handleMouseExit(mouseEvent: sfxi.MouseEvent): Unit = {
  }

  def updateMouseInfo(w: Point): Unit = {
    for {
      target <- entityViewer.target(w)
    } {
      entityHighlighted.update(target)
    }
  }

  def mouseToPoint(mouseEvent: sfxi.MouseEvent): Option[Point] = console.toScreen(mouseEvent.x, mouseEvent.y)
}

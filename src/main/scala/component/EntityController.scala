package component

import javafx.fxml.FXML
import javafx.scene.layout.Pane

import me.mtrupkin.console.Screen
import me.mtrupkin.control.ConsoleFx
import me.mtrupkin.core.{Size, Point}
import model.EntityViewer
import model.space.Entity

import scalafx.Includes._
import scalafx.beans.property.{ObjectProperty, Property}
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 5/22/2015.
 */
class EntityController {
//  @FXML var header: Label = _
  @FXML var consoleParent: Pane = _
  @FXML var targetReadout: Pane = _
  @FXML var targetReadoutController: EntityReadoutController = _

  val entityHighlighted = new ObjectProperty[Entity]
  val entitySelected = new ObjectProperty[Entity]

  var console: ConsoleFx = _
  var screen: Screen = _
  var entity: Entity = _
  var entityViewer: EntityViewer = _

  def initialize(): Unit = {
    val consoleSize = Size(40, 20)
    console = new ConsoleFx(consoleSize, fontSize = 24)
    console.setStyle("-fx-border-color: white")
    screen = Screen(consoleSize)

    consoleParent.onMouseMoved = (e: sfxi.MouseEvent) => handleMouseMove(e)
    consoleParent.onMouseClicked = (e: sfxi.MouseEvent) => handleMouseClicked(e)
    consoleParent.onMouseExited = (e: sfxi.MouseEvent) => handleMouseExit(e)

    consoleParent.getChildren.add(console)
    consoleParent.setFocusTraversable(true)
  }

  def setEntity(entity: Entity) {
    screen.clear()
    this.entity = entity
    this.entityViewer = EntityViewer(entity)
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
      entitySelected.update(target)
    }
  }

  def handleMouseExit(mouseEvent: sfxi.MouseEvent): Unit = {
  }

  def updateMouseInfo(w: Point): Unit = {
    targetReadoutController.updateCursor(w)

    for {
      target <- entityViewer.target(w)
    } {
      targetReadoutController.update(target)

      entityHighlighted.update(target)
    }
  }

  def mouseToPoint(mouseEvent: sfxi.MouseEvent): Option[Point] = console.toScreen(mouseEvent.x, mouseEvent.y)
}

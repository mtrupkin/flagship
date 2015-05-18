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
import scalafx.scene.{input, layout}
import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl, shape => sfxs, text => sfxt}

/**
 * Created by mtrupkin on 5/16/2015.
 */
trait SystemView { self: FlagshipController =>
  class SystemViewController(val world: Universe with UniverseTracker) extends ControllerState {
    val name = "SystemView"
    @FXML var headerLabel: Label = _
    @FXML var targetPositionLabel: Label = _
    @FXML var consoleParent: Pane = _

    var console: ConsoleFx = _
    var screen: Screen = _

    def initialize(): Unit = {
      val consoleSize = Size(40, 20)
      console = new ConsoleFx(consoleSize, fontSize = 24)
      console.setStyle("-fx-border-color: white")

      consoleParent.onMouseMoved = (e: sfxi.MouseEvent) => handleMouseMove(e)
      consoleParent.onMouseClicked = (e: sfxi.MouseEvent) => handleMouseClicked(e)
      consoleParent.onMouseExited = (e: sfxi.MouseEvent) => handleMouseExit(e)

      screen = Screen(consoleSize)
      consoleParent.getChildren.add(console)
      consoleParent.setFocusTraversable(true)
    }

    def handleMouseClicked(mouseEvent: sfxi.MouseEvent): Unit = {
      for( s <- mouseToPoint(mouseEvent)) {
        updateMouseInfo(s)
      }
    }

    def handleMouseMove(mouseEvent: sfxi.MouseEvent): Unit = {
      for( s <- mouseToPoint(mouseEvent)) {
        updateMouseInfo(s)
      }
    }

    def handleMouseExit(mouseEvent: sfxi.MouseEvent): Unit = {
      targetPositionLabel.setText("")
    }

    def updateMouseInfo(w: Point): Unit = {
      targetPositionLabel.setText(s"${w.x}:${w.y}")
    }

    def mouseToPoint(mouseEvent: sfxi.MouseEvent): Option[Point] = console.toScreen(mouseEvent.x, mouseEvent.y)

  }
}

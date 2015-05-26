package controller

import javafx.animation.{Animation, Timeline, FadeTransition}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Label, Button}
import javafx.scene.layout.Pane
import javafx.util.Duration

import me.mtrupkin.console.{ConsoleKey, Modifiers}
import me.mtrupkin.core.{Point, Points, Size}
import model.{SectorBuilder, Universe, Sector, UniverseTracker}

import scalafx.Includes._
import scalafx.scene.{control => sfxc, input => sfxi, layout => sfxl}


/**
 * Created by mtrupkin on 12/15/2014.
 */


trait Intro { self: MainController =>

  class IntroController extends ControllerState {
    val name = "intro"

    @FXML var pane: Pane = _
    @FXML var continueGameButton: Button = _
    @FXML var titleText: Label = _

    def initialize(): Unit = {
      new sfxl.Pane(pane) {
        onKeyPressed = (e: sfxi.KeyEvent) => handleKeyPressed(e)
      }

      val ft = new FadeTransition(Duration.millis(2000), titleText)
      ft.setFromValue(1.0)
      ft.setToValue(0.5)
      ft.setCycleCount(Animation.INDEFINITE)
      ft.setAutoReverse(true)
      ft.play()
    }

    def continueGame() = {
      val sectorBuilder = new SectorBuilder()
      val sector = sectorBuilder.apply()
      val universe = new Universe(Seq(sector)) with UniverseTracker
      changeState(new GameController(universe))
    }

    def handleContinueGame(event: ActionEvent) = continueGame()

    def handleExit(event: ActionEvent) = stage.close()

    def handleKeyPressed(event: sfxi.KeyEvent): Unit = {
      import me.mtrupkin.console.Key._
      val key = keyCodeToConsoleKey(event)
      key match {
        case ConsoleKey(X, Modifiers.Control) => exit()
        case ConsoleKey(k, _) => k match {
          case Enter => continueGame()
          case _ =>
        }
        case _ =>
      }
    }
  }
}

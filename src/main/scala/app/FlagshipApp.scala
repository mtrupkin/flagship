package app

import javafx.application.Application
import javafx.stage.Stage

import controller.FlagshipController
import me.mtrupkin.game.app.ConsoleAppBase

import scalafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}

class FlagshipApp extends ConsoleAppBase {
  val title = "Flagship"
  def controller(primaryStage: Stage) = new FlagshipController {
    lazy val initialState: ControllerState = new IntroController
    lazy val stage = primaryStage
//    stage.setFullScreen(true)
    stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE, KeyCombination.ShiftDown))
  }
}

object FlagshipApp extends App {
	Application.launch(classOf[FlagshipApp], args: _*)
}

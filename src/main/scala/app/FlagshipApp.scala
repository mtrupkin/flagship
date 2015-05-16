package app

import javafx.application.Application
import javafx.scene.text.{FontWeight, Font}
import javafx.stage.Stage

import controller.FlagshipController
import me.mtrupkin.control.ConsoleFx
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

  val font = Font.font("Consolas", FontWeight.NORMAL, 24)
  val font2 = Font.font("Consolas", FontWeight.NORMAL, 11)
  println(ConsoleFx.charBounds(font))
  println(ConsoleFx.charBounds(font2))
}

object FlagshipApp extends App {
	Application.launch(classOf[FlagshipApp], args: _*)
}

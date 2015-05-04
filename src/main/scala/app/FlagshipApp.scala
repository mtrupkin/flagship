package app

import javafx.application.Application
import javafx.stage.Stage

import controller.FlagshipController
import me.mtrupkin.game.app.ConsoleAppBase

class FlagshipApp extends ConsoleAppBase {
  val title = "Console App"
  def controller(primaryStage: Stage) = new FlagshipController {
    lazy val initialState: ControllerState = new IntroController
    lazy val stage = primaryStage
  }
}

object FlagshipApp extends App {
	Application.launch(classOf[FlagshipApp], args: _*)
}
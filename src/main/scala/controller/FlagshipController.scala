package controller

import me.mtrupkin.controller.Controller

/**
 * Created by mtrupkin on 5/3/2015.
 */
trait FlagshipController extends Controller
  with Intro
  with Game
  with SystemView {

  def exit(): Unit =  stage.close()

}

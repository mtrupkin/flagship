package controller

import me.mtrupkin.controller.Controller

/**
 * Created by mtrupkin on 5/3/2015.
 */
trait MainController extends Controller
  with Intro
  with Game {

  def exit(): Unit =  stage.close()

}

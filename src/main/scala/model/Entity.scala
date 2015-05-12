package model

import me.mtrupkin.console.{ScreenChar, Screen}
import me.mtrupkin.core.{Size, Point}

import scala.Array._

/**
 * Created by mtrupkin on 5/4/2015.
 */
trait Entity {
  def name: String
  def position: Point
  def update(elapsed: Int): Unit
  def render(screen: Screen): Unit = {
    screen.size.foreach {
      p => screen(p) = draw(p)
    }
  }
  def draw(p: Point): ScreenChar
}


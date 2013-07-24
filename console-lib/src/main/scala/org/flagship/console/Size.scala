package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */

trait Dimension {
  def width: Int
  def height: Int
}

case class Size(width: Int, height: Int) extends Dimension


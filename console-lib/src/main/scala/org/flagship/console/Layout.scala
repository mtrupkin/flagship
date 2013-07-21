package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/21/13
 */
case class LayoutData(snap: Boolean, grab: Boolean)

case class Layout(
  left: LayoutData = LayoutData.NONE,
  right: LayoutData = LayoutData.NONE,
  top: LayoutData = LayoutData.NONE,
  bottom: LayoutData = LayoutData.NONE)

object LayoutData {
  val NONE: LayoutData = new LayoutData(false, false)
}
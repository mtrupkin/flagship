package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/7/13
 */
object ConsoleKey extends Enumeration {
  val Shift = Value("Shift")
  val Control = Value("Control")
  val Alt = Value("Alt")
}

case class ConsoleKeyModifier(shift: Boolean, control: Boolean, alt: Boolean)

case class ConsoleKey(keyValue: scala.swing.event.Key.Value, modifier: ConsoleKeyModifier)
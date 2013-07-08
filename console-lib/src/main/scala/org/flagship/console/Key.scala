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
class ConsoleKeyModifier(val shift:Boolean, val control:Boolean, val alt:Boolean)

class ConsoleKey(val keyValue: scala.swing.event.Key.Value, val modifier:ConsoleKeyModifier)
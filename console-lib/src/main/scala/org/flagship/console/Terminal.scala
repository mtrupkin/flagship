package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
trait Terminal {
  val terminalSize: Size
  var closed = false
  var key: Option[ConsoleKey] = None

  def flush(screen: Screen)
}

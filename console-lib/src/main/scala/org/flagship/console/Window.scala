package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window extends Control {
  def render(s: Screen) {
    import s._
    import s.size._

    // corners
    write(0, 0, ACS.ULCORNER)
    write(width - 1, 0, ACS.URCORNER)
    write(0, height - 1, ACS.LLCORNER)
    write(width - 1, height - 1, ACS.LRCORNER)

    for(x <- 1 until width-1 ) {
      write(x, 0, ACS.HLINE)
      write(x, height - 1, ACS.HLINE)
    }

    for(y <- 1 until height-1 ) {
      write(0, y, ACS.VLINE)
      write(0 + width - 1, y, ACS.VLINE)
    }

    // Write the title
    //write(2, 0, title);
  }
}

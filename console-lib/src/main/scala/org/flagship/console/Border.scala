package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/9/13
 */
trait Border extends Control {

  abstract override def render(screen: Screen) {
    import screen.write

    // corners
    write(0, 0, ACS.ULCORNER)
    write(width - 1, 0, ACS.URCORNER)
    write(0, height - 1, ACS.LLCORNER)
    write(width - 1, height - 1, ACS.LRCORNER)

    // sides
    for(x <- 1 until width-1 ) {
      write(x, 0, ACS.HLINE)
      write(x, height - 1, ACS.HLINE)
    }

    for(y <- 1 until height-1 ) {
      write(0, y, ACS.VLINE)
      write(0 + width - 1, y, ACS.VLINE)
    }

    // display title
//    if (title != None) {
//      write(2, 0, title.get)
//    }

    super.render(screen)
  }
}

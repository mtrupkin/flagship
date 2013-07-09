package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/6/13
 */
class Window(val size: Size, val title: Option[String] = None) extends Composite {

  override def render(screen: Screen) {
    super.render(screen)
    import size._
    import screen._


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
    if (title != None) {
      write(2, 0, title.get)
    }
  }
}

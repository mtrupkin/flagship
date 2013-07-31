package flagship.console.widget

import flagship.console.control.{Control}
import org.flagship.console.{Size, Point}
import flagship.console.terminal.{ACS, Screen}


/**
 * User: mtrupkin
 * Date: 7/9/13
 */
trait Border extends Control {

  abstract override def minSize: Size = { Size(super.minSize.width +2, super.minSize.height +2)}

  abstract override def render(screen: Screen) {
    import screen.write
    val width =  this.dimension.width
    val height =  this.dimension.height

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
    val controlScreen = Screen(dimension)
    super.render(controlScreen)
    screen.display(1, 1, controlScreen)
  }

  abstract override def grab(size: Size): Unit = {
    val controlSize = Size(size.width - 2, size.height - 2)
    super.grab(controlSize)

  }

  abstract override def snap(size: Size) {
    val controlSize = Size(size.width - 2, size.height - 2)
    super.snap(controlSize)
  }
}

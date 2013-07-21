package org.flagship.console

/**
 * User: mtrupkin
 * Date: 7/8/13
 */
class Panel extends Composite {
  def minSize: Dimension = {
    var width = 1
    var height = 1

    controls.foreach(c => {
      if (c.width > width)
        width = c.width
      if (c.height > height)
        height = c.height } )


    new Size(width, height)
  }
}

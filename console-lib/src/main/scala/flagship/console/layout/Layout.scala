package flagship.console.layout

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

object Layout {
  val NONE: Layout = new Layout()
}

object LayoutData {
  val NONE: LayoutData = new LayoutData(false, false)
  val GRAB: LayoutData = new LayoutData(false, true)
  val SNAP: LayoutData = new LayoutData(true, false)

}
package org.flagship.console

import javax.swing.{JComponent, SwingUtilities, JFrame}
import java.awt._

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class SwingTerminal(val size: Size = new Size(100, 40)) extends Terminal {
  val terminalRenderer = new TerminalRenderer(size)

  var fg: Color = Color.White
  var bg: Color = Color.Black

  def display() = {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        createFrame()
      }
    })
  }

  def createFrame(): Unit = {
    val jframe = new JFrame("Terminal")
    jframe.getContentPane().setLayout(new BorderLayout());
    jframe.getContentPane().add(terminalRenderer, BorderLayout.CENTER);
    //terminalFrame.addKeyListener(new KeyCapturer());
    jframe.pack()
    //jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE)
    jframe.setLocationByPlatform(true)
    jframe.setVisible(true)
    jframe.setFocusTraversalKeysEnabled(false)
    jframe.pack()

  }

  def clearScreen() = {
  }

  def putCharacter(x: Int, y: Int, c: Char) = {
  }
}

class TerminalRenderer(val terminalSize: Size) extends JComponent
{
   val normalTextFont = new Font("Courier New", Font.PLAIN, 14)

  override def getPreferredSize(): Dimension =
  {
    val fontMetrics = getGraphics().getFontMetrics(normalTextFont)
    val screenWidth = terminalSize.width * fontMetrics.charWidth(' ')
    val screenHeight = terminalSize.height * fontMetrics.getHeight()
    new Dimension(screenWidth, screenHeight)
  }

  override def paintComponent(g: Graphics) =
{
  val g2 = g match {
    case g2: Graphics2D => g2
    case _ => throw new ClassCastException
  }

  g2.drawString("test", 100, 10)



}
}

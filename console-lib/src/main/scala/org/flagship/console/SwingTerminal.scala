package org.flagship.console

import javax.swing.{WindowConstants, JFrame}
import java.awt._
import java.awt.event._
import java.awt.image.BufferStrategy

/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class SwingTerminal(val terminalSize: Size = new Size(100, 40)) extends JFrame("Flagship") with Terminal {
  val terminalCanvas = new TerminalCanvas(terminalSize)

  //var fg: Color = Color.White
  //var bg: Color = Color.Black

  setIgnoreRepaint(true)
  getContentPane().setLayout(new BorderLayout())
  getContentPane().add(terminalCanvas, BorderLayout.CENTER)


  addKeyListener(new KeyListener {
    def keyTyped(e: KeyEvent) {}

    def keyPressed(e: KeyEvent) {}

    def keyReleased(e: KeyEvent) {}
  })
  addWindowListener(new WindowAdapter {
    override def windowClosed(e: WindowEvent) {
      closed = true
    }
  })

  pack()
  setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  setLocationByPlatform(true)
  setVisible(true)
  setFocusTraversalKeysEnabled(false)
  setResizable(false)
  pack()
  terminalCanvas.init()

  def clearScreen() = {
  }

  def putCharacter(x: Int, y: Int, c: Char) = {
  }

  def flush() {
    if (!closed) {
      terminalCanvas.flush()
    }
  }
}

class TerminalCanvas(val terminalSize: Size) extends Canvas {
  val normalTextFont = new Font("Courier New", Font.PLAIN, 14)
  setIgnoreRepaint(true)

  def init() = {
    createBufferStrategy(2)
  }

  def getGraphics2D(buffer: BufferStrategy): Graphics2D = {
    val g = buffer.getDrawGraphics
    g match {
      case g2: Graphics2D => g2
      case _ => throw new ClassCastException
    }
  }

  def flush() = {
    val buffer = getBufferStrategy
    val g2 = getGraphics2D(buffer)
    draw(g2)

    if (!buffer.contentsLost()) {
      buffer.show()
    }

    g2.dispose()
  }

  override def getPreferredSize(): Dimension = {
    val fontMetrics = getGraphics().getFontMetrics(normalTextFont)
    val screenWidth = terminalSize.width * fontMetrics.charWidth(' ')
    val screenHeight = terminalSize.height * fontMetrics.getHeight()
    new Dimension(screenWidth, screenHeight)
  }


  var lastPaintTime = System.currentTimeMillis()
  var frameCount = 0

  def draw(g2: Graphics2D) = {
    g2.setColor(java.awt.Color.BLACK)
    g2.fillRect(0, 0, getPreferredSize().getWidth.toInt, getPreferredSize().getHeight.toInt)
    g2.setColor(java.awt.Color.WHITE)

    val currentTime = System.currentTimeMillis()
    val time = currentTime - lastPaintTime
    lastPaintTime = currentTime
    frameCount = frameCount + 1

    g2.drawString(time.toString, 100, 10)
    g2.drawString(frameCount.toString, 100, 20)


  }
}

package org.flagship.console

import javax.swing.{WindowConstants}

import java.awt.image.BufferStrategy
import java.awt._
import scala.swing.{BorderPanel, Frame}
import scala.swing.event.{KeyReleased, Key, KeyPressed}
import java.awt.event.{KeyEvent, KeyAdapter}


/**
 * User: mtrupkin
 * Date: 7/5/13
 */
class SwingTerminal(val terminalSize: Size = new Size(100, 40)) extends Frame with Terminal {
  val terminalCanvas = new TerminalCanvas(terminalSize)
  val normalTextFont = new Font("Courier New", Font.PLAIN, 14)
  val buffer = Array.ofDim[ScreenCharacter](terminalSize.width, terminalSize.height)
  var consoleKey: ConsoleKey = null

  ignoreRepaint = true
  peer.add(terminalCanvas)
  peer.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      println("key pressed: " + e.getKeyChar)
      val modifiers = new ConsoleKeyModifier(e.isShiftDown, e.isAltDown, e.isControlDown)
      consoleKey = new ConsoleKey( Key(e.getKeyCode),modifiers )

    }
  })




//  addKeyListener(new KeyAdapter {
//    override def keyTyped(e: KeyEvent) {}
//  })
//
//  val mouseAdapter = new MouseAdapter {
//  }
//  addMouseListener(mouseAdapter)
//  addMouseMotionListener(mouseAdapter)
//

  pack()
  override def closeOperation( ) {
    closed = true
    visible = false
    dispose()
  }
  //closeOperation()
  //peer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  //setLocationByPlatform(true)
  visible = true
  //setFocusTraversalKeysEnabled(false)
  resizable = false
  pack()
  terminalCanvas.init()

  def clearScreen() = {
  }

  def putCharacter(x: Int, y: Int, c: Char) = {
    buffer(x)(y) = ScreenCharacter(c, fg, bg)
  }

  def flush() {
    if (!closed) {
      terminalCanvas.flush()
    }
  }

  def key() = {
    consoleKey
  }
  class TerminalCanvas(val terminalSize: Size) extends Canvas {

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
      val screenDim = screenSize(charSize(getGraphics()))
      new Dimension(screenDim.width, screenDim.height)
    }

    def charSize(g: Graphics): Size = {
      val fontMetrics = g.getFontMetrics(normalTextFont)
      new Size(fontMetrics.charWidth(' '), fontMetrics.getAscent())
    }

    def screenSize(charSize: Size): Size = {
      new Size(terminalSize.width * charSize.width, terminalSize.height * charSize.height)
    }

    var lastPaintTime = System.currentTimeMillis()
    var frameCount = 0

    def draw(g2: Graphics2D) = {
      this.synchronized {
        val charDim = charSize(g2)
        val screenDim = screenSize(charDim)

        g2.setColor(java.awt.Color.BLACK)
        g2.fillRect(0, 0, screenDim.width, screenDim.height)

        g2.setColor(java.awt.Color.WHITE)

        val currentTime = System.currentTimeMillis()
        val time = currentTime - lastPaintTime
        lastPaintTime = currentTime
        frameCount = frameCount + 1

        drawString((1000f/time).toString, 1, 1)
        drawString(frameCount.toString, 1, 2)

        for {
          i <- buffer.indices
          j <- buffer(i).indices
        } drawScreenCharacter( i, j, buffer(i)(j))

        def drawScreenCharacter(x:Int, y: Int, s: ScreenCharacter) = {
          drawString(s.c.toString, x, y)
        }

        def drawString(s: String, x:Int, y: Int) = {
          g2.drawString(s, x * charDim.width - 3, (y+1) * charDim.height - 3)
        }
      }
    }
  }
}




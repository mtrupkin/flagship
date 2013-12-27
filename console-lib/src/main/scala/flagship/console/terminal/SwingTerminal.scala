package flagship.console.terminal

import javax.swing.WindowConstants

import java.awt.image.BufferStrategy
import java.awt._
import scala.swing.{BorderPanel, Frame}
import scala.swing.event.{KeyReleased, Key, KeyPressed}
import java.awt.event.{MouseEvent, MouseAdapter, KeyEvent, KeyAdapter}
import flagship.console.input.{ConsoleKey, ConsoleKeyModifier}
import org.flagship.console._
import scala.Some
import flagship.console.input.ConsoleKeyModifier
import org.flagship.console.{Size, Point}


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

class SwingTerminal(val terminalSize: Size = new Size(50, 20), windowTitle: String = "Swing Terminal") extends Frame with Terminal {
  val terminalCanvas = new TerminalCanvas(terminalSize)
  val normalTextFont = new Font("Courier New", Font.PLAIN, 14)
  //"SansSerif"
  val systemFont = new Font("Arial", Font.PLAIN, 10)

  title = windowTitle
  ignoreRepaint = true
  visible = true
  resizable = false

  peer.add(terminalCanvas)

  terminalCanvas.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      val modifiers = new ConsoleKeyModifier(e.isShiftDown, e.isAltDown, e.isControlDown)
      key = Some(new ConsoleKey( Key(e.getKeyCode),modifiers ))
    }

    override def keyReleased(e: KeyEvent) {
      key = None
    }
  })

  val mouseAdapter = new MouseAdapter {
    override def mouseMoved(e: MouseEvent) {
      //println(e.getX, e.getY)
    }
  }

  terminalCanvas.addMouseListener(mouseAdapter)
  terminalCanvas.addMouseMotionListener(mouseAdapter)

  override def closeOperation( ) {
    closed = true
    visible = false
    dispose()
  }

  pack()
  terminalCanvas.createBufferStrategy(2)


  def flush(screen: Screen) {
    if (!closed) {
      terminalCanvas.flush(screen)
    }
  }

  class TerminalCanvas(val terminalSize: Size) extends Canvas {

    def getGraphics2D(g: Graphics): Graphics2D = {
      g match {
        case g2: Graphics2D => g2
        case _ => throw new ClassCastException
      }
    }

    def getGraphics2D(buffer: BufferStrategy): Graphics2D = {
      getGraphics2D(buffer.getDrawGraphics)
    }

    override def getPreferredSize(): java.awt.Dimension = {
      val screenDim = screenSize(charSize(getGraphics()))
      new java.awt.Dimension(screenDim.width, screenDim.height)
    }

    def charSize(g: Graphics): Size = {

      val fontMetrics = g.getFontMetrics(normalTextFont)
      //val bounds = fontMetrics.getStringBounds(" ", g)
      //val bounds = fontMetrics.getStringBounds(ACS.VLINE.toString, g)
      val width = fontMetrics.charWidth(ACS.HLINE)

      //new Size(width , fontMetrics.getAscent()*2)
      //new Size(bounds.getWidth.toInt+1, bounds.getHeight.toInt+2)
      new Size(width, fontMetrics.getHeight)
    }

    def screenSize(charSize: Size): Size = {
      new Size(terminalSize.width * charSize.width, terminalSize.height * charSize.height)
    }

    var lastPaintTime = System.currentTimeMillis()
    var frameCount = 0

    def flush(screen: Screen) = {

      val buffer = getBufferStrategy
      val g2 = getGraphics2D(buffer)

      render(screen, g2)

      if (!buffer.contentsLost()) {
        buffer.show()
      }

      g2.dispose()
    }

    def flushSingleBuffer(screen: Screen) = {
      val g2 = getGraphics2D(getGraphics())
      render(screen, g2)

      g2.dispose()
    }

    def render(screen: Screen, g2: Graphics2D) = {
      this.synchronized {
        g2.setFont(normalTextFont)

        val charDim = charSize(g2)
        val screenDim = screenSize(charDim)

        g2.setColor(java.awt.Color.BLACK)
        g2.fillRect(0, 0, screenDim.width, screenDim.height)

        g2.setColor(java.awt.Color.WHITE)


        screen.foreach(drawScreenCharacter)

        def drawScreenCharacter(p: Point, s: ScreenCharacter) {
          drawString(p, s.c.toString)
        }

        def drawString(p: Point, s: String) {
          val p2 = toPixel(p)
          g2.drawString(s, p2.x, p2.y)
        }

        def toPixel(p: Point): Point = {
          Point(p.x * charDim.width, ((p.y+1) * charDim.height) - 4)
        }

        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastPaintTime
        val frameRate = (1000f/elapsedTime)
        lastPaintTime = currentTime
        frameCount = frameCount + 1

//        drawString(frameRate.toString, 1, 1)
//        drawString(frameCount.toString, 1, 2)

        g2.setFont(systemFont)
        val p1 = toPixel(Point(90, 35))
        //g2.drawString(frameRate.toString, p1.x, p1.y)
        val p2 = toPixel(Point(90, 36))
        //g2.drawString(frameCount.toString, p2.x, p2.y)

      }
    }
  }
}




package v6.views.tabs

import v6.models.Point

import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.JPanel

class KmeansView extends JPanel with TabbedView with KmeansObserver {

  private var points : Array[Point] = Array.empty
  private var centers : Array[Point] = Array.empty
  private var vector : Array[Int] = Array.empty

  private val colors : Array[Color] = Array(
    Color.BLUE,
    Color.RED,
    Color.GREEN,
    Color.PINK,
    Color.ORANGE,
    Color.CYAN,
    Color.YELLOW
  )

  override def paintComponent(graphics : Graphics) : Unit = {

    super.paintComponent(graphics)

    val g2d = graphics.create.asInstanceOf[Graphics2D]

    // To set the graphic at the bottom left and corner.
    g2d.scale(1, -1)
    g2d.translate(0, -this.getHeight)

    this.drawPoints(g2d)
    this.drawCentroids(g2d)
  }

  override def update(points: Array[Point], centers: Array[Point], vector: Array[Int]): Unit = {
    this.points = points
    this.centers = centers
    this.vector = vector
  }

  override def display(): Unit = this.repaint()

  override def refresh(): Unit = this.repaint()

  override def title: String = "Kmeans Visualization"

  private def drawPoints(g2d : Graphics2D) : Unit = {

    for(i <- this.points.indices) {

      val point : Point = this.points(i)
      val color = if(this.vector(i) < this.colors.length) this.colors(this.vector(i)) else Color.GRAY

      g2d.setColor(color)
      g2d.drawOval((point.x * 100).toInt, (point.y * 100).toInt, 5, 5)
    }
  }

  private def drawCentroids(g2d : Graphics2D) : Unit = {

    g2d.setColor(Color.BLACK)

    for(i <- this.centers.indices) {

      val point : Point = this.centers(i)

      g2d.drawOval((point.x * 100).toInt, (point.y * 100).toInt, 5, 5)
    }
  }
}

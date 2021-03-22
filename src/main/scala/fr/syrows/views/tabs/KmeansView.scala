package fr.syrows.views.tabs

import fr.syrows.models.Point
import fr.syrows.models.kmeans.KmeansModel
import fr.syrows.views.View

import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.JPanel

class KmeansView extends JPanel with TabbedView with View[KmeansModel] {

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

    if(this.getModel == null) return

    val g2d = graphics.create.asInstanceOf[Graphics2D]

    // To set the graphic at the bottom left and corner.
    g2d.scale(1, -1)
    g2d.translate(0, -this.getHeight)

    this.drawPoints(g2d)
    this.drawCentroids(g2d)
  }

  override def display(): Unit = this.repaint()

  override def refresh(): Unit = this.repaint()

  override def title: String = "Visualization"

  private def drawPoints(g2d : Graphics2D) : Unit = {

    val model : KmeansModel = super.getModel

    for(i <- model.getData.indices) {

      val point : Point = model.getData(i)
      val color = if(model.getVector(i) < this.colors.length) this.colors(model.getVector(i)) else Color.GRAY

      this.drawPoint(g2d, point, color)
    }
  }

  private def drawCentroids(g2d : Graphics2D) : Unit = {

    val model : KmeansModel = super.getModel

    for(i <- model.getCentroids.indices) {

      val point : Point = model.getCentroids(i)
      this.drawPoint(g2d, point, Color.BLACK)
    }
  }

  private def drawPoint(g2d: Graphics2D, point : Point, color : Color) : Unit = {
    g2d.setColor(color)
    g2d.drawOval((point.x * 80).toInt, (point.y * 80).toInt, 5, 5)
    g2d.fillOval((point.x * 80).toInt, (point.y * 80).toInt, 5, 5)
  }
}

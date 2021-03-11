package v3

import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.JPanel

class Graph(val points : Array[Point], val vector : Array[Int], val centers : Array[Point]) extends JPanel {

  override def paintComponent(g : Graphics): Unit = {

    super.paintComponent(g)

    val g2d = g.create.asInstanceOf[Graphics2D]

    g2d.scale(1, -1)
    g2d.translate(0, -this.getHeight)

    for(i <- this.points.indices) {

      val point : Point = this.points(i)

      if(vector(i) == 0) g2d.setColor(Color.BLUE)
      else if(vector(i) == 1) g2d.setColor(Color.RED)
      else g2d.setColor(Color.GREEN)

      g2d.drawOval((point.x * 100).toInt, (point.y * 100).toInt, 5, 5)
    }

    for(i <- this.centers.indices) {

      val point : Point = this.centers(i)

      g2d.setColor(Color.BLACK)
      g2d.drawOval((point.x * 100).toInt, (point.y * 100).toInt, 5, 5)
    }
  }
}

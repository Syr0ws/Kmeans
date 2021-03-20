package v3

import javax.swing.{JFrame, JPanel}

object Main {

  def main(args: Array[String]): Unit = {

    val content : Array[String] = FileUtils.readResource("iris.data")
    val matrix : Array[Array[String]] = FileUtils.toMatrix(content)

    val points : Array[Point] = new Array[Point](matrix.length)

    for(row <- matrix.indices) points(row) = new Point(matrix(row)(0).toDouble, matrix(row)(1).toDouble)

    val kmeans : Kmeans = new Kmeans(points, 3, 50)
    val vector : (Array[Int], Array[Point]) = kmeans.execute()

    val frame : JFrame = new JFrame()
    frame.setVisible(true)
    frame.add(new Graph(points, vector._1, vector._2))
    frame.setDefaultCloseOperation(1)
  }
}

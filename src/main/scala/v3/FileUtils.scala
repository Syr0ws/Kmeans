package v3

import scala.io.{BufferedSource, Source}

object FileUtils {

  def readResource(resource: String): Array[String] = {

    val source: BufferedSource = Source.fromResource(resource)
    val iterator: Iterator[String] = source.getLines()

    val array: Array[String] = iterator.toArray

    source.close()

    array
  }

  def toMatrix(array: Array[String]): Array[Array[String]] = {

    if (array.length == 0) return Array.ofDim(0, 0)

    var matrix: Array[Array[String]] = null

    for (i <- array.indices) {

      val line: String = array(i)
      val split = line.split(",")

      if (matrix == null) matrix = Array.ofDim(array.length, split.length)

      matrix(i) = split
    }
    matrix
  }
}

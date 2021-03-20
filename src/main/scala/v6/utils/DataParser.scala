package v6.utils

object DataParser {

  def toDoubleArray(array : Array[String]) : Array[Array[Double]] = {

    if (array.length == 0) return Array.ofDim(0, 0)

    var matrix: Array[Array[Double]] = null

    for (i <- array.indices) {

      val line : String = array(i)
      val split : Array[String] = line.split(",")

      if (matrix == null) matrix = Array.ofDim(array.length, split.length)

      val data : Array[Double] = Array.ofDim(split.length - 1)

      for(j <- data.indices) data(j) = split(j).toDouble

      matrix(i) = data
    }
    matrix
  }
}

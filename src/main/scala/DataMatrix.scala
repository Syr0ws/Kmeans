class DataMatrix(val matrix : Array[Array[String]]) {

  def average(index : Int) : Double = {

    var sum : Double = 0

    this.iterate((value : Double) => sum += value, index)

    sum / matrix.length
  }

  def variance(index : Int) : Double = {

    val avg : Double = average(index)
    var sum : Double = 0

    this.iterate((value : Double) => sum += Math.pow(avg - value, 2), index)

    sum / this.matrix.length
  }

  def stdDeviation(index : Int) : Double = Math.sqrt(variance(index))

  def averages(indexes : Array[Int]): Array[Double] = this.indexes(indexes, (index : Int) => average(index))

  def variances(indexes : Array[Int]) : Array[Double] = this.indexes(indexes, (index : Int) => variance(index))

  def stdDeviations(indexes : Array[Int]) : Array[Double] = this.indexes(indexes, (index : Int) => stdDeviation(index))

  private def iterate(function : Double => Unit, index : Int): Unit = {

    for(i <- matrix.indices) function(matrix(i)(index).toDouble)
  }

  private def indexes(indexes : Array[Int], function : (Int) => Double) = {

    val array : Array[Double] = new Array[Double](indexes.length)

    for(i <- indexes.indices) array(i) = function(i)

    array
  }
}

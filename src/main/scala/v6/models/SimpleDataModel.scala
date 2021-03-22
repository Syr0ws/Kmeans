package v6.models

class SimpleDataModel extends DataModel {

  private var data : Array[Array[Double]] = Array.ofDim(0, 0)

  override def getColumns: Int = this.data(0).length

  override def average(column: Int): Double = {

    var sum : Double = 0

    this.readColumn((value : Double) => sum += value, column)

    sum / this.count()
  }

  override def variance(column: Int): Double = {

    val avg : Double = average(column)
    var sum : Double = 0

    this.readColumn((value : Double) => sum += Math.pow(avg - value, 2), column)

    sum / this.count()
  }

  override def standardDeviation(column: Int): Double = Math.sqrt(variance(column))

  override def correlation(v1: Int, v2: Int): Double = {

    val average1 = this.average(v1)
    val average2 = this.average(v2)

    val standardDeviation1 = this.standardDeviation(v1)
    val standardDeviation2 = this.standardDeviation(v2)

    var covariance : Double = 0

    for(i <- this.data.indices) covariance += (this.data(i)(v1) - average1) * (this.data(i)(v2) - average2)

    // [sum(x - average(x)) * sum(y - average(y))] / sqrt(sum(x - average(x))^2 * sum(y - average(y))^2))
    (covariance / this.count()) / (standardDeviation1 * standardDeviation2)
  }

  override def averages(): Array[Double] = {

    val averages : Array[Double] = new Array[Double](this.getColumns)

    for(i <- averages.indices) averages(i) = average(i)

    averages
  }

  override def variances(): Array[Double] = {

    val variances : Array[Double] = new Array[Double](this.getColumns)

    for(i <- variances.indices) variances(i) = variance(i)

    variances
  }

  override def standardDeviations(): Array[Double] = {

    val stdDeviations : Array[Double] = new Array[Double](this.getColumns)

    for(i <- stdDeviations.indices) stdDeviations(i) = standardDeviation(i)

    stdDeviations
  }

  override def correlations(): Array[Array[Double]] = {

    val array : Array[Array[Double]] = Array.ofDim(this.getColumns, this.getColumns)

    for(i <- array.indices) {

      for(j <- array(i).indices) array(i)(j) = this.correlation(i, j)
    }
    array
  }

  override def count() : Int = if(this.data == null) 0 else this.data.length

  override def toPointArray(v1: Int, v2: Int): Array[Point] = {

    val points : Array[Point] = Array.ofDim(this.count())

    for(i <- this.data.indices) points(i) = new Point(this.data(i)(v1), this.data(i)(v2))

    points
  }

  override def getData: Array[Array[Double]] = this.data

  override def setData(array: Array[Array[Double]]): Unit = {

    require(array != null, "Data array cannot be null.")

    this.data = array
    this.notifyObservers()
  }

  private def readColumn(function : Double => Unit, index : Int): Unit = {

    for(i <- this.data.indices) function(this.data(i)(index))
  }
}

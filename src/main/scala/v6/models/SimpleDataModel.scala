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

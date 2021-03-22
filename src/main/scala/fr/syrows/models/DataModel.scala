package fr.syrows.models

trait DataModel extends Model {

  def average(column : Int) : Double

  def variance(column : Int) : Double

  def standardDeviation(column : Int) : Double

  def correlation(v1 : Int, v2 : Int) : Double

  def averages() : Array[Double]

  def variances() : Array[Double]

  def standardDeviations() : Array[Double]

  def correlations() : Array[Array[Double]]

  def toPointArray(v1 : Int, v2 : Int) : Array[Point]

  def count() : Int

  def getColumns: Int

  def getData: Array[Array[Double]]

  def setData(array : Array[Array[Double]])
}

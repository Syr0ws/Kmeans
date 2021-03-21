package v6.models.kmeans

import v6.models.{Model, Point}

trait KmeansModel extends Model {

  def next(): Unit

  def hasNext: Boolean

  def getData : Array[Point]

  def getCenters : Array[Point]

  def getVector : Array[Int]
}

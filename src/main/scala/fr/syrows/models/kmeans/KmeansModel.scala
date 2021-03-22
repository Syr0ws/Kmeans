package fr.syrows.models.kmeans

import fr.syrows.models.{Model, Point}

trait KmeansModel extends Model {

  /**
   * Run the next iteration of the Kmeans algorithm.
   */
  def next(): Unit

  /**
   * Check if the algorithm is finished.
   * @return true if the algorithm has a next iteration (isn't finished) of else false.
   */
  def hasNext: Boolean

  /**
   * The data used into the algorithm.
   * @return an array of Point.
   */
  def getData : Array[Point]

  /**
   * Get the current centroids.
   * @return an array of Point.
   */
  def getCentroids : Array[Point]

  /**
   * Get the current vector that contains the classes of which each point belongs to.
   * @return an array of Integer.
   */
  def getVector : Array[Int]
}

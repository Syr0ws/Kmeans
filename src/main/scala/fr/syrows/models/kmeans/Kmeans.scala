package fr.syrows.models.kmeans

import fr.syrows.models.Point

import scala.util.Random

class Kmeans(val data : Array[Point], val clusters : Int, val iterations : Int) extends KmeansModel {

  require(data.length >= 1, "Array must contains more than 1 value.")
  require(clusters >= 1, "Number of clusters must be greater or equal than 1.")
  require(clusters <= this.data.length, "Number of clusters must be lower than the number of values.")
  require(iterations >= 1, "Number of iterations must be greater or equal than 1.")

  private val RANDOM : Random = new Random

  private var centroids : Array[Point] = _
  private var vector : Array[Int] = new Array[Int](this.data.length)
  private var iteration : Int = 0

  override def next(): Unit = {

    if(!this.hasNext) return

    this.centroids = if(this.centroids == null) this.generateRandomCentroids() else this.getNewCentroids(this.vector)
    this.vector = this.generateClassVector(this.centroids)

    this.notifyObservers()

    this.iteration += 1
  }

  override def hasNext: Boolean = this.iteration < this.iterations

  override def getData: Array[Point] = this.data

  override def getCentroids: Array[Point] = this.centroids

  override def getVector: Array[Int] = this.vector

  /**
   * Generate random centroids which are random points of the data. This function
   * ensure that two centroids cannot be the sames.
   *
   * @return an array of Point.
   */
  private def generateRandomCentroids() : Array[Point] = {

    val centers : Array[Point] = new Array[Point](this.clusters)

    for(index <- centers.indices) {

      var randomIndex = RANDOM.nextInt(this.data.length) // Finding a random index.
      var point = this.data(randomIndex) // Retrieving the associated point.

      // If the point is already a center, repeating the operation.
      while(centers.contains(point)) {

        randomIndex = RANDOM.nextInt(this.data.length)
        point = this.data(randomIndex)
      }
      centers(index) = point // Registering the center.
    }
    centers
  }

  /**
   * Get the class of a point belongs to based on the euclidean distance between centroids.
   *
   * @param centroids an array of Point that corresponds to the current centroids.
   * @param point an object of type Point to search the class of.
   *
   * @return the index of the center the point is the nearest.
   */
  private def getPointClass(centroids : Array[Point], point : Point) : Int = {

    var centerIndex : Int = 0

    for(index <- 1 until centroids.length) {

      val center = centroids(index) // Getting the center point according to its index.

      if(center.equals(point)) return index // Do not change the center

      // Calculating euclidean distances.
      val distance1 : Double = centroids(centerIndex).euclideanDistance(point)
      val distance2 : Double = center.euclideanDistance(point)

      // If the new distance is lower than the stored one, changing center.
      if(distance2 < distance1) centerIndex = index
    }
    centerIndex
  }

  /**
   * Generate a vector that contains the classes of which each point belongs to.
   *
   * @param centroids an array of Point that corresponds to the current centroids.
   * @return an array of Integers.
   */
  private def generateClassVector(centroids : Array[Point]) : Array[Int] = {

    val vector : Array[Int] = new Array[Int](this.data.length)

    for(i <- this.data.indices) {

      // Getting the class of the point.
      // A class is represented by the index of the nearest center of the point.
      val centerClass : Int = this.getPointClass(centroids, this.data(i))
      vector(i) = centerClass
    }
    vector
  }

  /**
   * Find new centroids of each cluster.
   *
   * @param vector an array of Integers that contains the classes of which each point belongs to.
   * @return an array of Point which contains the new centroids of each cluster.
   */
  private def getNewCentroids(vector : Array[Int]) : Array[Point] = {

    val centers : Array[Point] = new Array[Point](this.clusters)

    // For each cluster, calculating its new center.
    for(centerIndex <- centers.indices) {

      var x : Double = 0
      var y : Double = 0
      var count : Int = 0

      for(i <- this.data.indices) {

        // Calculating only for the concerned cluster.
        if(vector(i) == centerIndex) {

          val point = this.data(i)

          x += point.x
          y += point.y
          count += 1
        }
      }
      // The center is the average of the vectors.
      centers(centerIndex) = new Point(x / count, y / count)
    }
    centers
  }
}

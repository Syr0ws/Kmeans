package v6.models.kmeans

import v6.models.Point

import scala.util.Random

class Kmeans(val data : Array[Point], val clusters : Int, val iterations : Int) extends KmeansModel {

  require(data.length >= 1, "Array must contains more than 1 value.")
  require(clusters >= 1, "Number of clusters must be greater or equal than 1.")
  require(clusters <= this.data.length, "Number of clusters must be lower than the number of values.")
  require(iterations >= 1, "Number of iterations must be greater or equal than 1.")

  private val RANDOM : Random = new Random

  private var centers : Array[Point] = this.generateRandomCenters()
  private var vector : Array[Int] = new Array[Int](this.data.length)
  private var iteration : Int = 0

  override def next(): Unit = {

    if(!this.hasNext) return

    this.vector = this.generateClassVector(this.centers)
    this.centers = this.getNewCenters(this.vector)

    this.notifyObservers()

    this.iteration += 1
  }

  override def hasNext: Boolean = this.iteration < this.iterations

  override def getData: Array[Point] = this.data

  override def getCenters: Array[Point] = this.centers

  override def getVector: Array[Int] = this.vector

  private def generateRandomCenters() : Array[Point] = {

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

  private def getCenterClass(centers : Array[Point], point : Point) : Int = {

    var centerIndex : Int = 0

    for(index <- 1 until centers.length) {

      val center = centers(index) // Getting the center point according to its index.

      // Calculating euclidean distances.
      val distance1 : Double = centers(centerIndex).euclideanDistance(point)
      val distance2 : Double = center.euclideanDistance(point)

      // If the new distance is lower than the stored one, changing center.
      if(distance2 < distance1) centerIndex = index
    }
    centerIndex
  }

  private def generateClassVector(centers : Array[Point]) : Array[Int] = {

    val vector : Array[Int] = new Array[Int](this.data.length)

    for(i <- this.data.indices) {

      // Getting the class of the point.
      val centerClass : Int = this.getCenterClass(centers, this.data(i))
      vector(i) = centerClass
    }
    vector
  }

  private def getNewCenters(vector : Array[Int]) : Array[Point] = {

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

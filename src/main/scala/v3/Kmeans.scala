package v3

import scala.util.Random

class Kmeans(val matrix : Array[Point], val clusters : Int, val iterations : Int) {

  private val RANDOM : Random = new Random

  require(matrix.length >= 1, "Array must contains more than 1 value.")
  require(clusters >= 1, "Number of clusters must be greater or equal than 1.")
  require(clusters <= this.matrix.length, "Number of clusters must be lower than the number of values.")
  require(iterations >= 1, "Number of iterations must be greater or equal than 1.")

  def execute() : (Array[Int], Array[Point]) = {

    var centers : Array[Point] = this.generateRandomCenters()
    var vector : Array[Int] = new Array[Int](this.matrix.length)

    for(_ <- 0 to this.iterations) {

      vector = this.generateClassVector(centers)
      centers = this.getNewCenters(vector)
    }
    (vector, centers)
  }

  // Checked
  private def generateRandomCenters() : Array[Point] = {

    val centers : Array[Point] = new Array[Point](this.clusters)

    for(index <- centers.indices) {

      var randomIndex = RANDOM.nextInt(this.matrix.length) // Finding a random index.
      var point = this.matrix(randomIndex) // Retrieving the associated point.

      // If the point is already a center, repeating the operation.
      while(centers.contains(point)) {

        randomIndex = RANDOM.nextInt(this.matrix.length)
        point = this.matrix(randomIndex)
      }
      centers(index) = point // Registering the center.
    }
    centers
  }

  // Je récupère le cluster auquel le point appartient.
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

    val vector : Array[Int] = new Array[Int](this.matrix.length)

    for(i <- this.matrix.indices) {

      // Getting the class of the point.
      val centerClass : Int = this.getCenterClass(centers, this.matrix(i))
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

      for(i <- this.matrix.indices) {

        // Calculating only for the concerned cluster.
        if(vector(i) == centerIndex) {

          val point = this.matrix(i)

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
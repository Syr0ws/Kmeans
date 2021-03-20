package v6.models

import v6.views.tabs.KmeansView

import scala.util.Random

class KmeansAlgorithm extends Model {

  private val RANDOM : Random = new Random
  private var kmeansView : KmeansView = _

  def executeAlgorithm(data : Array[Point], clusters : Int, iterations : Int) : Unit = {

    require(data.length >= 1, "Data must be composed of at least one value.")
    require(clusters >= 1, "Number of clusters must be greater or equal than 1.")
    require(clusters <= data.length, "Number of clusters must be lower than the number of values.")
    require(iterations >= 1, "Number of iterations must be greater or equal than 1.")

    var centers : Array[Point] = this.generateRandomCenters(data, clusters)
    var vector : Array[Int] = new Array[Int](data.length)

    for(_ <- 0 to iterations) {

      vector = this.generateClassVector(data, centers)
      centers = this.getNewCenters(data, clusters, vector)
      
      this.kmeansView.update(data, centers, vector)
      this.kmeansView.refresh()
    }
  }

  private def generateRandomCenters(data : Array[Point], clusters : Int) : Array[Point] = {

    val centers : Array[Point] = new Array[Point](clusters)

    for(index <- centers.indices) {

      var randomIndex = RANDOM.nextInt(data.length) // Finding a random index.
      var point = data(randomIndex) // Retrieving the associated point.

      // If the point is already a center, repeating the operation.
      while(centers.contains(point)) {

        randomIndex = RANDOM.nextInt(data.length)
        point = data(randomIndex)
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

  private def generateClassVector(data : Array[Point], centers : Array[Point]) : Array[Int] = {

    val vector : Array[Int] = new Array[Int](data.length)

    for(i <- data.indices) {

      // Getting the class of the point.
      val centerClass : Int = this.getCenterClass(centers, data(i))
      vector(i) = centerClass
    }
    vector
  }

  private def getNewCenters(data : Array[Point], clusters : Int, vector : Array[Int]) : Array[Point] = {

    val centers : Array[Point] = new Array[Point](clusters)

    // For each cluster, calculating its new center.
    for(centerIndex <- centers.indices) {

      var x : Double = 0
      var y : Double = 0
      var count : Int = 0

      for(i <- data.indices) {

        // Calculating only for the concerned cluster.
        if(vector(i) == centerIndex) {

          val point = data(i)

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

  def setView(view : KmeansView) : Unit = this.kmeansView = view
}

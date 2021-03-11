class Kmeans(val matrix : DataMatrix, val k : Int, val n : Int) {

  def distance(x1 : Double, y1 : Double, x2 : Double, y2 : Double): Double =
    Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))


}

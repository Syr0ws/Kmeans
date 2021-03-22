package fr.syrows.models

class Point(val x : Double, val y : Double) {

  def euclideanDistance(point : Point) : Double = {
    Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2))
  }

  override def toString: String = s"${this.x}  ${this.y}"

  override def equals(obj : Any): Boolean = {

    obj match {
      case point : Point => point.x == this.x && point.y == this.y && point.getClass == this.getClass
      case _ => false
    }
  }
}

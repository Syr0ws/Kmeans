package v6.views.tabs

import v6.models.{KmeansAlgorithm, Point}
import v6.views.View

trait KmeansObserver extends View[KmeansAlgorithm] {

  def update(points : Array[Point], centers : Array[Point], vector : Array[Int])
}

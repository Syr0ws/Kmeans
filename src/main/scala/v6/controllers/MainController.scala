package v6.controllers

import v6.models.{DataModel, Point}
import v6.models.kmeans.Kmeans

import java.util.{Timer, TimerTask}

class MainController extends ApplicationController {

  override def executeKmeans(): Unit = {

    require(this.getView != null, "No view set.")

    val k : Int = getK

    // Invalid number of clusters.
    if(k == -1) return

    val iterations : Int = getIterations

    // Invalid number of iterations.
    if(iterations == -1) return

    val variables : Array[Int] = this.getVariables

    // Invalid variables selected.
    if(variables.isEmpty) return

    val model : DataModel = this.getModel
    val data : Array[Point] = model.toPointArray(variables(0), variables(1))
    val kmeans : Kmeans = new Kmeans(data, k, iterations)

    this.getView.bindKmeansModel(kmeans)

    if(this.getView.getDisplaySteps) this.executeKmeansTask(kmeans)
    else while(kmeans.hasNext) kmeans.next()
  }

  private def executeKmeansTask(kmeans : Kmeans) : Unit = {

    val timer = new Timer()
    val task = new TimerTask {

      override def run(): Unit = {

        if(!kmeans.hasNext) this.cancel()
        else kmeans.next()
      }
    }
    timer.schedule(task, 0L, 1000L)
  }

  private def getK: Int = {

    val k : String = this.getView.getK
    val option = k.toIntOption

    if(option.isEmpty || option.get < 1) {

      this.getView.displayError("K doit être un nombre positif supérieur à 1.")
      -1

    } else option.get
  }

  private def getIterations: Int = {

    val iterations : String = this.getView.getIterations
    val option = iterations.toIntOption

    if(option.isEmpty || option.get < 2) {

      this.getView.displayError("Le nombre d'itérations doit être supérieur ou égal à 2.")
      -1

    } else option.get
  }

  private def getVariables : Array[Int] = {

    val variables : Array[Int] = this.getView.getVariables

    if(variables.isEmpty || variables(0) == -1 || variables(1) == -1) {
      this.getView.displayError("Veuillez sélectionner deux variables.")
      return Array.empty
    }

    variables
  }
}

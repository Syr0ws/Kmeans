package fr.syrows.views

import fr.syrows.controllers.ApplicationController
import fr.syrows.models.DataModel
import fr.syrows.models.kmeans.KmeansModel

trait ApplicationView extends View[DataModel] {

  private var controller : ApplicationController = _

  def getK : String

  def getIterations : String

  def getVariables : Array[Int]

  def getDisplaySteps : Boolean

  def displayError(message : String) : Unit

  def bindKmeansModel(model : KmeansModel) : Unit

  def getController : ApplicationController = this.controller

  def setController(controller : ApplicationController) : Unit = this.controller = controller
}

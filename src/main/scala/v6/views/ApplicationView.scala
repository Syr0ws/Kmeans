package v6.views

import v6.controllers.ApplicationController
import v6.models.DataModel
import v6.models.kmeans.KmeansModel

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

package v6.views

import v6.controllers.ApplicationController
import v6.models.DataModel

trait ApplicationView extends View[DataModel] {

  private var controller : ApplicationController = _

  def getK : String

  def getIterations : String

  def getVariables : Array[Int]

  def displayError(message : String) : Unit

  def getController : ApplicationController = this.controller

  def setController(controller : ApplicationController) : Unit = this.controller = controller
}

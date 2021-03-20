package v6.controllers

import v6.models.{DataModel, SimpleDataModel}
import v6.views.ApplicationView

trait ApplicationController {

  private var model : DataModel = _
  private var view : ApplicationView = _

  def executeKmeans() : Unit

  def setModel(model : DataModel) = this.model = model

  def getModel : DataModel = this.model

  def getView : ApplicationView = this.view

  def setView(view : ApplicationView) : Unit = this.view = view
}

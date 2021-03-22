package fr.syrows.controllers

import fr.syrows.models.DataModel
import fr.syrows.views.ApplicationView

trait ApplicationController {

  private var model : DataModel = _
  private var view : ApplicationView = _

  def executeKmeans() : Unit

  def setModel(model : DataModel) = this.model = model

  def getModel : DataModel = this.model

  def getView : ApplicationView = this.view

  def setView(view : ApplicationView) : Unit = this.view = view
}

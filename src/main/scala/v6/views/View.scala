package v6.views

import v6.models.Model

trait View[A <: Model] {

  private var model : A = _

  def display()

  def refresh()

  def setModel(model: A) : Unit = {
    this.model = model
    this.refresh()
  }

  def getModel : A = this.model
}

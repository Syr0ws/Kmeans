package fr.syrows.models

import fr.syrows.views.View

import scala.collection.mutable.ListBuffer

trait Model {

  private val observers: ListBuffer[View[_ <: Model]] = ListBuffer.empty

  def addObserver(view: View[_ <: Model]): Unit = this.observers.addOne(view)

  def removeObserver(view: Class[View[Model]]): Unit = {
    this.observers.filter(obs => obs.getClass == view)
      .map(obs => this.observers.indexOf(obs))
      .foreach(index => this.observers.remove(index))
  }

  def notifyObservers(): Unit = this.observers.foreach(obs => obs.refresh())

  def getObservers : ListBuffer[View[_ <: Model]] = this.observers
}

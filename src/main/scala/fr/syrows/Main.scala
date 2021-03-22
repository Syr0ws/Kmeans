package fr.syrows

import fr.syrows.controllers.{ApplicationController, MainController}
import fr.syrows.models.{DataModel, SimpleDataModel}
import fr.syrows.utils.{DataParser, FileUtils}
import fr.syrows.views.{ApplicationView, MainView}

object Main {

  def main(args: Array[String]): Unit = {

    val content : Array[String] = FileUtils.readResource("iris.data")
    val data : Array[Array[Double]] = DataParser.toDoubleArray(content)

    val model : DataModel = new SimpleDataModel
    model.setData(data)

    val application : ApplicationView = new MainView
    application.setModel(model)

    val controller : ApplicationController = new MainController
    controller.setModel(model)
    controller.setView(application)

    application.setController(controller)
    application.display()
  }
}

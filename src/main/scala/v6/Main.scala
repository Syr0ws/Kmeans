package v6

import v6.controllers.{ApplicationController, MainController}
import v6.models.{DataModel, SimpleDataModel}
import v6.utils.{DataParser, FileUtils}
import v6.views.{ApplicationView, MainView}

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

package v6.views.tabs.tables

import v6.models.DataModel

class StatsTableView extends TableView {

  override def header(): Array[AnyRef] = {

    val columns : Int = this.getModel.getColumns
    val header : Array[AnyRef] = Array.ofDim(columns + 1)

    header(0) = ""

    for(column <- 1 to columns) header(column) = s"x${column - 1}"

    header
  }

  override def data(): Array[Array[AnyRef]] = {

    val model : DataModel = this.getModel
    val data : Array[Array[AnyRef]] = Array.ofDim(3, model.getColumns)

    data(0) = Array("Moyenne").concat(model.averages().map(_.asInstanceOf[AnyRef]))
    data(1) = Array("Variance").concat(model.variances().map(_.asInstanceOf[AnyRef]))
    data(2) = Array("Écart-type").concat(model.standardDeviations().map(_.asInstanceOf[AnyRef]))

    data
  }

  override def title: String = "Statistiques"
}

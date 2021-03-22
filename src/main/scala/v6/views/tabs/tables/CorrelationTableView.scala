package v6.views.tabs.tables

import v6.models.DataModel

class CorrelationTableView extends TableView {

  override def header(): Array[AnyRef] = {

    val columns : Int = this.getModel.getColumns
    val header : Array[AnyRef] = Array.ofDim(columns + 1)

    header(0) = ""

    for(column <- 1 to columns) header(column) = s"x${column - 1}"

    header
  }

  override def data(): Array[Array[AnyRef]] = {

    val model : DataModel = this.getModel
    val correlations : Array[Array[Double]] = model.correlations()
    val data : Array[Array[AnyRef]] = Array.ofDim(correlations.length)

    for(row <- data.indices) data(row) = Array(s"x$row").concat(correlations(row).map(_.asInstanceOf[AnyRef]))

    data
  }

  override def title: String = "Corrélation"
}

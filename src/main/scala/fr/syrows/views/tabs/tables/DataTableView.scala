package fr.syrows.views.tabs.tables

import fr.syrows.models.DataModel

class DataTableView extends TableView {

  override def header(): Array[AnyRef] = {

    val columns : Int = this.getModel.getColumns
    val header : Array[AnyRef] = Array.ofDim(columns)

    for(column <- 0 until columns) header(column) = s"x$column"

    header
  }

  override def data(): Array[Array[AnyRef]] = {

    val model : DataModel = this.getModel
    val data : Array[Array[AnyRef]] = Array.ofDim(model.count(), model.getColumns)

    for(row <- 0 until model.count()) {

      for(column <- 0 until model.getColumns) data(row)(column) = model.getData(row)(column).asInstanceOf[AnyRef]
    }
    data
  }

  override def title: String = "Données"
}

package v6.views.tabs.tables

import v6.models.DataModel
import v6.views
import v6.views.tabs.TabbedView

import javax.swing.table.DefaultTableModel
import javax.swing.{JScrollPane, JTable}

abstract class TableView extends JScrollPane with views.View[DataModel] with TabbedView {

  private val table : JTable = new JTable()

  super.setViewportView(this.table)

  def header() : Array[AnyRef]

  def data() : Array[Array[AnyRef]]

  override def display(): Unit = {

    val model : DataModel = getModel

    if(model.count() == 0) return

    val tableModel : DefaultTableModel = new DefaultTableModel(this.data(), this.header())

    this.table.setModel(tableModel)
  }

  override def refresh(): Unit = this.display()
}

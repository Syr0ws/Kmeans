package fr.syrows.views

import fr.syrows.models.DataModel

import javax.swing.{JCheckBox, JPanel}
import scala.collection.mutable.ListBuffer

class VariableSelectorView extends JPanel with View[DataModel] {

  private val boxes : ListBuffer[JCheckBox] = ListBuffer.empty

  override def display(): Unit = {

    val model : DataModel = super.getModel

    val columns : Int = model.getColumns
    val array : Array[JCheckBox] = Array.ofDim(columns)

    for(i <- array.indices) {

      val box : JCheckBox = new JCheckBox(s"x$i")

      this.boxes.addOne(box)
      this.add(box)
    }
  }

  override def refresh(): Unit = {
    this.boxes.foreach(box => this.remove(box))
    this.boxes.clear()
    this.display()
  }

  def getSelectedBoxes : Array[Int] = {

    val selected : Array[Int] = Array(-1, -1)

    for(i <- this.boxes.indices) {

      if(this.boxes(i).isSelected) {

        if(selected(0) == -1) selected(0) = i
        else if(selected(1) == -1) selected(1) = i
        else return Array.empty
      }
    }
    selected
  }
}

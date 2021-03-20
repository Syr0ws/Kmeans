package v6.views

import v6.models.DataModel
import v6.views.tabs.KmeansView
import v6.views.tabs.tables.{DataTableView, StatsTableView}

import java.awt.{BorderLayout, Toolkit}
import javax.swing._
import scala.collection.mutable.ListBuffer

class MainView extends JFrame with ApplicationView {

  private val kInput : JTextField = new JTextField(3)
  private val iterationInput : JTextField = new JTextField(3)

  private val views : ListBuffer[View[DataModel]] = ListBuffer.empty
  private var checkBoxes : Array[JCheckBox] = Array.empty

  override def display(): Unit = {

    require(this.getModel != null, "Model not defined.")

    val topPanel = this.buildTopPanel()
    val middlePanel = this.buildMiddlePanel()
    val bottomPanel = this.buildBottomPanel()

    this.add(topPanel, BorderLayout.PAGE_START)
    this.add(middlePanel, BorderLayout.CENTER)
    this.add(bottomPanel, BorderLayout.PAGE_END)

    this.views.foreach(view => {
      this.getModel.addObserver(view)
      view.display()
    })

    this.center(0.5)
    this.setTitle("Kmeans visualization")
    this.setVisible(true)
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  }

  override def refresh(): Unit = {

    this.kInput.setText("")
    this.iterationInput.setText("")
  }

  override def getK: String = this.kInput.getText

  override def getIterations: String = this.iterationInput.getText

  override def getVariables: Array[Int] = {

    val selected : Array[Int] = Array(-1, -1)

    for(i <- this.checkBoxes.indices) {

      if(this.checkBoxes(i).isSelected) {

        if(selected(0) == -1) selected(0) = i
        else if(selected(1) == -1) selected(1) = i
        else return Array.empty
      }
    }
    selected
  }

  override def displayError(message: String): Unit = JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE)

  override def getModel: DataModel = super.getModel

  private def buildTopPanel() : JPanel = {

    val panel : JPanel = new JPanel()

    this.checkBoxes = this.buildCheckBoxList()
    this.checkBoxes.foreach(box => panel.add(box))

    panel
  }

  private def buildBottomPanel() : JPanel = {

    val panel : JPanel = new JPanel()

    panel.add(new JLabel("K:"))
    panel.add(this.kInput)

    panel.add(new JLabel("Itérations:"))
    panel.add(this.iterationInput)

    panel.add(this.getExecuteButton, BorderLayout.LINE_END)

    panel
  }

  private def buildMiddlePanel() : JTabbedPane = {

    val pane : JTabbedPane = new JTabbedPane()

    val kmeansView = new KmeansView
    val dataView = new DataTableView
    val statsView = new StatsTableView

    dataView.setModel(this.getModel)
    statsView.setModel(this.getModel)
    kmeansView.setModel(getModel.getKmeansAlgorithm)
    getModel.getKmeansAlgorithm.setView(kmeansView) // TODO To change.

    this.views.addOne(dataView)
    this.views.addOne(statsView)

    pane.addTab(kmeansView.title, kmeansView)
    pane.addTab(dataView.title, dataView)
    pane.addTab(statsView.title, statsView)

    pane
  }

  private def buildCheckBoxList() : Array[JCheckBox] = {

    val columns : Int = this.getModel.getColumns
    val array : Array[JCheckBox] = Array.ofDim(columns)

    for(i <- array.indices) array(i) = new JCheckBox(s"x$i")

    array
  }

  private def getExecuteButton : JButton = {

    val button : JButton = new JButton()

    button.setText("Exécuter")
    button.addActionListener(_ => this.getController.executeKmeans())

    button
  }

  private def center(ratio : Double): Unit = {

    val tk = Toolkit.getDefaultToolkit
    val dim = tk.getScreenSize

    val width = (ratio * dim.width).toInt
    val height = (ratio * dim.height).toInt

    this.setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height)
  }
}

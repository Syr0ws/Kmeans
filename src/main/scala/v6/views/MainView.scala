package v6.views

import v6.models.DataModel
import v6.models.kmeans.KmeansModel
import v6.views.tabs.KmeansView
import v6.views.tabs.tables.{DataTableView, StatsTableView}

import java.awt.{BorderLayout, Toolkit}
import javax.swing._
import scala.collection.mutable.ListBuffer

class MainView extends JFrame with ApplicationView {

  private val kInput : JTextField = new JTextField(3)
  private val iterationInput : JTextField = new JTextField(3)
  private val displaySteps : JCheckBox = new JCheckBox()

  private val variableView : VariableSelectorView = new VariableSelectorView
  private val kmeansView : KmeansView = new KmeansView

  private val views : ListBuffer[View[DataModel]] = ListBuffer.empty

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
      view.setModel(getModel)
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

  override def bindKmeansModel(model: KmeansModel): Unit = {
    model.addObserver(this.kmeansView)
    this.kmeansView.setModel(model)
  }

  override def getK: String = this.kInput.getText

  override def getIterations: String = this.iterationInput.getText

  override def getVariables: Array[Int] = this.variableView.getSelectedBoxes

  override def getDisplaySteps: Boolean = this.displaySteps.isSelected

  override def displayError(message: String): Unit = JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE)

  private def buildTopPanel() : JPanel = {

    this.views.addOne(this.variableView)
    this.variableView
  }

  private def buildBottomPanel() : JPanel = {

    val panel : JPanel = new JPanel()

    panel.add(this.displaySteps)
    panel.add(new JLabel("Afficher les étapes"))

    panel.add(new JLabel("K:"))
    panel.add(this.kInput)

    panel.add(new JLabel("Itérations:"))
    panel.add(this.iterationInput)

    panel.add(this.getExecuteButton, BorderLayout.LINE_END)

    panel
  }

  private def buildMiddlePanel() : JTabbedPane = {

    val pane : JTabbedPane = new JTabbedPane()

    val dataView = new DataTableView
    val statsView = new StatsTableView

    dataView.setModel(this.getModel)
    statsView.setModel(this.getModel)

    this.views.addOne(dataView)
    this.views.addOne(statsView)

    pane.addTab(this.kmeansView.title, this.kmeansView)
    pane.addTab(dataView.title, dataView)
    pane.addTab(statsView.title, statsView)

    pane
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

object Main {

  def main(args: Array[String]): Unit = {

    val array : Array[String] = FileUtils.readResource("iris.data")
    val matrix : DataMatrix = new DataMatrix(FileUtils.toMatrix(array))

    println(matrix.averages(Array(1, 2, 3, 4)).mkString("Array(", ", ", ")"))
    println(matrix.variances(Array(1, 2, 3, 4)).mkString("Array(", ", ", ")"))
    println(matrix.stdDeviations(Array(1, 2, 3, 4)).mkString("Array(", ", ", ")"))
  }
}

package fr.syrows.utils

import java.io.File
import scala.io.{BufferedSource, Source}

object FileUtils {

  def readResource(resource: String): Array[String] = {

    val source: BufferedSource = Source.fromResource(resource)
    val array: Array[String] = this.toArray(source)

    source.close()

    array
  }

  def readFile(file : File): Array[String] = {

    val source: BufferedSource = Source.fromFile(file)
    val array: Array[String] = this.toArray(source)

    source.close()

    array
  }

  private def toArray(source: BufferedSource): Array[String] = {

    val iterator: Iterator[String] = source.getLines()

    val array: Array[String] = iterator.toArray

    array
  }
}

package fr.syrows.utils

import fr.syrows.models.kmeans.Kmeans

import java.util.{Timer, TimerTask}

class Task(val kmeans : Kmeans) extends TimerTask {

  private var running : Boolean = false

  override def run(): Unit = {

    if(!this.kmeans.hasNext) this.cancel()
    else this.kmeans.next()
  }

  override def cancel(): Boolean = {
    this.running = false
    super.cancel()
  }

  def start() : Unit = {
    this.running = true
    new Timer().schedule(this, 0L, 500L)
  }

  def isRunning : Boolean = this.running
}

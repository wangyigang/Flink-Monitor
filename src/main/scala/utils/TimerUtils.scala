package utils

/**
  * Inspired by Apache Spark, implement System Clock
  */

trait Clock {
  def getTimeMillis(): Long
  def waitTillTime(targetTime: Long): Long
}

class SystemClock extends Clock {

  val minPollTime = 25L

  override def getTimeMillis(): Long = System.currentTimeMillis()

  override def waitTillTime(targetTime: Long): Long = {
    var currentTime = 0L
    currentTime = System.currentTimeMillis()

    var waitTime = targetTime - currentTime
    if (waitTime <= 0) {
      return currentTime
    }

    val pollTime = math.max(waitTime / 10.0, minPollTime).toLong

    while (true) {
      currentTime = System.currentTimeMillis()
      waitTime = targetTime - currentTime
      if (waitTime <= 0) {
        return currentTime
      }
      val sleepTime = math.min(waitTime, pollTime)
      Thread.sleep(sleepTime)
    }
    -1
  }
}

package scala

import java.sql.Timestamp
import java.util.Date

/**
  * Created by NaryshkinAA on 04.12.2017.
  */
trait Scheduler {
  def addJob(job:Job):Unit
}
trait Job {
  def startTime:Long
  def execute():Unit
}


package scala

import java.sql.Timestamp
import java.util.Date

/**
  * Created by NaryshkinAA on 04.12.2017.
  */
trait Scheduler {
  /**
    *  Добавление задания в планировщик, который исполняет задачи в заданное время.
    *  Если задачи запланированы на одно время, задачи будут выполнены в порядке постановки в очередь.
    * @param job
    */
  def addJob(job:Job):Unit
}
trait Job {
  def startTime:Long
  def execute():Unit
}


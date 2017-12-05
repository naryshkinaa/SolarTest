package scala.errors

/**
  * Created by NaryshkinAA on 04.12.2017.
  */
case class ExpiredJobException(job:Job) extends RuntimeException{
  override def getMessage: String = "Время выполнения задачи истекло. Задача не может быть выполнена."
}

package main

import java.util.Date

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorSystem, Cancellable, Props}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.errors.ExpiredJobException
import scala.util.Try
/**
  * Created by NaryshkinAA on 04.12.2017.
  */
class SchedulerImpl extends Scheduler{

  private val system = ActorSystem("test")
  private val actorRef = system.actorOf(Props(new JobActor()))
  def addJob(job: Job) = {
    if(job.startTime < new Date().getTime) throw ExpiredJobException(job)
    actorRef ! AddJob(job)
  }
}

class JobActor extends Actor{
  val jobs = collection.mutable.Map[Long,ArrayBuffer[Job]]()
  def receive: Receive = {
    case AddJob(job) =>
      val buffer = jobs.getOrElseUpdate(job.startTime,{
        context.system.scheduler.scheduleOnce((job.startTime - new Date().getTime) milliseconds,self,DoJob(job.startTime))(context.system.dispatcher)
        ArrayBuffer[Job]()
      })
      buffer += job
    case DoJob(time) => {
      jobs.get(time).foreach(f => f.foreach(e =>
        try{
          e.execute()
        }
        catch {
          case e:Throwable =>  println(e.getMessage)
        }
      ))
      jobs.remove(time)
    }
  }
}

case class AddJob(job:Job)
case class DoJob(time:Long)
package test

import java.util.Date

import main.SchedulerImpl
import org.junit.Test
import org.junit.Assert.assertEquals

import scala.errors.ExpiredJobException

/**
  * Created by NaryshkinAA on 04.12.2017.
  */
class SchedulerTest     {
  val schedulerImpl = new SchedulerImpl

  @Test
  def test1(){
    /**
      * Тест что все задачи выполняются
      */
    var counter = 0
    def job = new Job {
      def execute() = {
        counter+=1
      }
      val startTime = new Date().getTime() +500
    }
    schedulerImpl.addJob(job)
    schedulerImpl.addJob(job)
    Thread.sleep(2000) // waiting for job done
    assertEquals(counter,2)
  }
  @Test(expected = classOf[ExpiredJobException])
  def test2(){
    /**
      * Тест на не корретное время старта задачи
      */
    def job = new Job {
      def execute() = {}
      val startTime = new Date().getTime() -1000
    }
    schedulerImpl.addJob(job)
  }
  @Test
  def test3(){
    /**
      * Тест на последовательность выполения задач
      */
    val date = new Date().getTime()
    var result = ""
    def job1 = new Job {
      def execute() = {
        result+="1"
      }
      val startTime =  date + 1000
    }
    def job2 = new Job {
      def execute() = {
        result += "2"
      }
      val startTime =  date + 1010
    }
    def job3 = new Job {
      def execute() = {
        result += "3"
      }
      val startTime =  date + 990
    }
    def job4 = new Job {
      def execute() = {
        result += "4"
      }
      val startTime =  date + 1000
    }
    schedulerImpl.addJob(job1)
    schedulerImpl.addJob(job2)
    schedulerImpl.addJob(job3)
    schedulerImpl.addJob(job4)
    schedulerImpl.addJob(job1)
    schedulerImpl.addJob(job1)
    schedulerImpl.addJob(job1)
    Thread.sleep(2000) // waiting for job done
    assertEquals(result,"3141112")
  }
}

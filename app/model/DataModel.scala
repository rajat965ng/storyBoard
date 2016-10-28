package model

import model.DataModel.ColumnMapper.localDateTimeColumnType
import java.sql.Timestamp
import java.time.LocalDateTime

import model.DataModel.Story

import scala.concurrent.Await
import scala.concurrent.duration.DurationDouble
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

/**
  * Created by rajat on 25/10/16.
  */



object DataModel {


  case class Story(
                  title:String,
                  description:String,
                  createdDate:LocalDateTime = LocalDateTime.now(),
                  id:Long = 0L
                  )

  class StoryTable(tag: Tag) extends Table[Story](tag,"Stories"){

    def title = column[String]("title")
    def description = column[String]("description")
    def createdDate = column[LocalDateTime]("createdDate")(localDateTimeColumnType)
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    override def * : ProvenShape[Story] = (title,description,createdDate,id) <>(Story.tupled, Story.unapply)
  }

  lazy val Stories = TableQuery[StoryTable]
  val createStroryTableAction = Stories.schema.create
  def insertStoryAction(stories: Story*) = Stories ++= stories.toSeq
  val listStoryAction = Stories.result
  def deleteStoryAction(id:Long) = Stories.filter(_.id===id).delete

  object ColumnMapper{

   implicit val localDateTimeColumnType = MappedColumnType.base[LocalDateTime, Timestamp](
     ldt => Timestamp.valueOf(ldt),
     t => t.toLocalDateTime
   )

  }

}

class StoryDao{

  val db = Database.forConfig("storyDb")


  def create() = {
    val result = Await.result(db.run(DataModel.createStroryTableAction), 2 seconds)
    println(result)
    println("=======create============")
    println(result.getClass)
  }

  def insert(story: DataModel.Story) = {
    val result = Await.result(db.run(DataModel.insertStoryAction(story)),2 seconds)
    println(result)
    println("========insert===========")
    println(result.getClass)
    println(list)
  }

  def list:List[Story] = {
    val result = Await.result(db.run(DataModel.listStoryAction), 2 seconds)
    println(result)
    println("========list===========")
    result.toList
  }

  def delete(id:Long) = {
    val result = Await.result(db.run(DataModel.deleteStoryAction(id)),2 seconds)
    println(result)
    println("========delete===========")
    println(result.getClass)
  }

}

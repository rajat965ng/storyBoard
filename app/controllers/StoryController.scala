package controllers

import controllers.StoryModel.StoryData
import javax.inject.Inject

import play.api.Configuration
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import model.StoryDao
import model.DataModel.Story

import scala.collection.mutable.ListBuffer
/**
  * Created by rajat on 27/10/16.
  */
class StoryController @Inject()(implicit storyDao:StoryDao,config:Configuration) extends Controller{

  def index = Action {
    val result = storyDao.create();

    Ok(views.html.story("Welcome to StoryBoard !!",listStory))
  }

  def createStory = Action { implicit Request =>
    var result = storyDao.insert(new Story(storyForm.bindFromRequest().get.title,storyForm.bindFromRequest().get.description))
    Ok(views.html.story("Story Created:" + "(" + storyForm.bindFromRequest().get.title + "," + storyForm.bindFromRequest().get.description + ")",listStory))

  }

  def listStory:List[StoryData] = {
    val stories:List[Story] = storyDao.list
    var storiesData = new ListBuffer[StoryData]
    for(s <- stories){
      storiesData += (new StoryData(s.title,s.description))
    }
    storiesData.toList
  }

  val storyForm = Form(
    mapping(
      "title" -> text,
      "description" -> text
    )(StoryData.apply)(StoryData.unapply)
  )

}

object StoryModel{
  case class StoryData(
                      title:String,
                      description:String
                      )
}
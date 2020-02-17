package controllers

import javax.inject._

import edu.trinity.videoquizreact.shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def tempPage = Action {
    Ok(views.html.tempPage((1946 to 2014).toList))
  }

  def temps(month: Int, year: Int) = {
    TODO
  }
}

package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index("The index page."))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product type is: $prodType, product number is $prodNum")
  }

  def tempPage = Action { implicit request =>
    Ok(views.html.tempPage((1946 to 2014).toList))
  }

  def temps(month: Int, year: Int) = Action { implicit request =>
    Ok(views.html.tempMonth(month, year, models.TempModel.data.
      filter(td => td.year == year && td.month == month)))
  }

  def randomNumber = Action { implicit request =>
    Ok(util.Random.nextInt(100).toString())
  }

  def randomString(length: Int) = Action {
    Ok(util.Random.nextString(length))
  }
}

package controllers

import javax.inject._
import play.api.i18n._
import play.api.mvc._
import models.TaskListDatabaseModel
import play.api.libs.json._
import models._

import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

@Singleton
class TaskList5 @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,  cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new TaskListDatabaseModel(db)

  def load = Action { implicit request =>
    Ok(views.html.version5Main())
  }

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]):Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match { 
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.TaskList3.load()))
      }
    }.getOrElse(Future.successful(Redirect(routes.TaskList3.load())))
    
  }

  def withSessionUsername(f: String => Future[Result])(implicit request: Request[AnyContent]):Future[Result] = {
    request.session.get("username").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  implicit val userDataReads = Json.reads[UserData]


  def validate = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.validateUser(ud.username, ud.password).map { userExists =>
        if (userExists) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def createUser = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.createUser(ud.username, ud.password).map { userCreated =>
        if (userCreated) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def taskList = Action.async { implicit request => 
    withSessionUsername { username =>
      model.getTasks(username).map(tasks => Ok(Json.toJson(tasks)))
    }
  }

  def addTask = TODO
  // Action.async { implicit request =>
  //   val usernameOpt = request.session.get("username")
  //   usernameOpt.map { username => 

  //     request.body.asJson.map { body =>
  //       Json.fromJson[String](body) match { 
  //         case JsSuccess(task, path) => 
  //           model.addTask(username, task);
  //           Ok(Json.toJson(true))
  //         case e @ JsError(_) => Redirect(routes.TaskList3.load())
  //       }
  //     }.getOrElse(Ok(Json.toJson(false)))
  //   }.getOrElse(Ok(Json.toJson(false)))
  // }

  def removeTask = TODO
  // Action.async { implicit request => 
  //   val usernameOpt = request.session.get("username")
  //     usernameOpt.map { username => 
  //       request.body.asJson.map { body =>
  //         Json.fromJson[Int](body) match { 
  //           case JsSuccess(index, path) => 
  //             model.removeTask(username, index);
  //             Ok(Json.toJson(true))
  //           case e @ JsError(_) => Redirect(routes.TaskList3.load())
  //         }
  //       }.getOrElse(Ok(Json.toJson(false)))
  //     }.getOrElse(Ok(Json.toJson(false)))
  // }

  def logout = Action { implicit request =>
    Ok(Json.toJson(true)).withNewSession
  }
}
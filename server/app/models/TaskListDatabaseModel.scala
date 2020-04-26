package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class TaskListDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
  def validateUser(username: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(userRow => userRow.username === username).result)
    matches.map(userRows => userRows.filter(userRow => BCrypt.checkpw(password, userRow.password))nonEmpty)
  }

  def createUser(username:String, password: String): Future[Boolean] = {
    db.run(Users+= UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
    .map(addCount => addCount > 0)
  }

  def getTasks(username: String): Future[Seq[String]] = {
    db.run(
      (for {
        user <- Users if user.username === username
        item <- Items if item.userId === user.id
      } yield {
        item.text
      }).result
    )
  }

  def addTask(username: String, task: String): Future[Int] = {
    ???
  }

  def removeTask(username: String, index: Int): Future[Boolean] = {
    ???
  }
}
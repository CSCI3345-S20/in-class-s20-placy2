import org.scalatestplus.play._
import models.TaskListInMemoryModel

class TaskListModelSpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("placy", "pass") mustBe true
    }

    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("placy", "password") mustBe false
    }

    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("parker", "pass") mustBe false
    }

    "reject login with wrong username and password" in {
      TaskListInMemoryModel.validateUser("parker", "password") mustBe false
    }

    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("placy") mustBe List("Webapps", "Software Engineering", "Music History", "Sleep")
    }

    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("parker", "password") mustBe true
      TaskListInMemoryModel.getTasks("parker") mustBe Nil
    }

    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("placy", "password") mustBe false
    }

    "add new task for default user" in {
      TaskListInMemoryModel.addTask("placy", "Testing")
      TaskListInMemoryModel.getTasks("placy") must contain("Testing")
    }

    "add new task for new user" in {
      TaskListInMemoryModel.addTask("parker", "Better testing")
      TaskListInMemoryModel.getTasks("parker") must contain("Better testing")
    }

    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("placy", TaskListInMemoryModel.getTasks("placy").indexOf("Webapps")) mustBe true
      TaskListInMemoryModel.getTasks("placy") must not contain ("Webapps")
    }
  }
}
import org.scalatestplus.play._
import controllers.Application
import play.api.test.Helpers
import play.api.test.Helpers._
import play.api.test.FakeRequest


class ControllerSpec extends PlaySpec {
  "Application#index" must {
    "give back expected page" in {
      val controller = new Application(Helpers.stubControllerComponents())
      val result = controller.index.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Play and Scala.js")
    }
  }
}
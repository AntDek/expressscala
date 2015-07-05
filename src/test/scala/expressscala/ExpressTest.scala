package expressscala

import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import dispatch._, Defaults._

@RunWith(classOf[JUnitRunner])
class ExpressTest extends FunSuite with BeforeAndAfterAll {

	val subscription = DemoServer.start
	val request = url("http://localhost:" + DemoServer.port + DemoServer.path)


	test("Server should serve get request") {
		val userJson = Http(request OK as.String)
		assert(Await.result(userJson, 5 second) == DemoServer.defaultUserJson)
	}

	test("Server should serve post request") {
		val requestAsJson = request.POST.setContentType("application/json", "UTF-8")
		val postWithBody = requestAsJson << DemoServer.defaultUserJson
		val userJson = Http(postWithBody OK as.String)
		assert(Await.result(userJson, 5 second) == DemoServer.defaultUserJson)
	}

	test("Server should serve put request") {
		val requestAsJson = request.PUT.setContentType("application/json", "UTF-8")
		val postWithBody = requestAsJson << DemoServer.defaultUserJson
		val userJson = Http(postWithBody OK as.String)
		assert(Await.result(userJson, 5 second) == DemoServer.defaultUserJson)
	}

	override def afterAll:Unit = {
		subscription.unsubscribe
	}

}
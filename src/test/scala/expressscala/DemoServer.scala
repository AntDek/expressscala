package expressscala
import expressscala.http.Request

import expressscala.http.Subscription

object DemoServer {
	case class User(val name: String, val email: String)

	val port: Int = 8081
	val path: String = "/demo/user"
	val defaultUserJson = """{"name":"Demo User","email":"demo.user@example.com"}"""
	val defaultUser = User("Demo User", "demo.user@example.com")

	def start: Subscription = {
		val app = Express(port)
		app.get(path) { r: Request =>
			println("run")
			jsonResponse[User](defaultUser)
		}

		app.post(path) { r =>
			val user = r.body.extract[User]
			jsonResponse[User](user)
		}

		app.put(path) { r =>
			val user = r.body.extract[User]
			jsonResponse[User](user)
		}

		app.start
	}
}
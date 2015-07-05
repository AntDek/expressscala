package expressscala

import expressscala.http._
import expressscala.httpserver._
import expressscala.Routes.HttpRoute
import scala.collection.mutable.MutableList
import scala.concurrent._
import ExecutionContext.Implicits.global

trait Express {

	val routes: Routes

	val listener: Listener

	def delete = routes.addHandler("DELETE") _

	def get = routes.addHandler("GET") _

	def post = routes.addHandler("POST") _

	def put = routes.addHandler("PUT") _

	def start = listener.start
}

object Express {

	def apply(port: Int): Express = new Express {
		val listener = SingleThreadListener(port)
		val routes = Routes()

		listener.listen("/") { req: Request =>
			val method = req.method
			val path = req.path

			val route = routes.findRoute(method, path)

			if(route.isEmpty) {
				notFoundResponse("Request method not found: " + method + " " + path)
			} else {
				route.get.handler(req)
			}
		}
	}
}
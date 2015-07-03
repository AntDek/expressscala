package expressscala

import expressscala.http._
import expressscala.httpserver._
import scala.collection.mutable.MutableList
import scala.concurrent._
import ExecutionContext.Implicits.global

trait Express {

	case class HttpRoute(method: String, path: String, handler: Request => Response)
	
	val routes: MutableList[HttpRoute]

	val listener: Listener

	def httpMethod(method: String, path: String, handler: Request => Response): Unit = {
		routes += new HttpRoute(method, path, handler)
	}

	def get(path: String)(handler: Request => Response) = httpMethod("GET", path, handler)

	def start = listener.start
}

object Express {

	def apply(port: Int): Express = new Express {
		val listener = SingleThreadListener(8081)
		val routes = MutableList[HttpRoute]()

		listener.listen("/") { req: Request =>
			val method = req.method
			val path = req.path

			val route = routes.find { r =>
				r.method == method && r.path == path
			}

			if(route.isEmpty) {
				Future { notFoundResponse("Request method not found: " + method + " " + path) }
			} else {
				Future { route.get.handler(req) }
			}
		}
	}
}
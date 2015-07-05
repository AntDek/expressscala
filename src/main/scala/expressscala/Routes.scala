package expressscala

import http._
import scala.collection.mutable.Map

trait Routes {
	import Routes.HttpRoute

	val routes: Map[String, HttpRoute]

	def addHandler(method: String)(path: String)(handler: Request => Response): Unit = {
		routes += (getId(method, path) -> new HttpRoute(method, path, handler))
	}

	def findRoute(method: String, path: String): Option[HttpRoute] = {
		routes.get(getId(method, path))
	}

	private def getId(method: String, path: String): String = {
		method + path
	}
}

object Routes {
	case class HttpRoute(method: String, path: String, handler: Request => Response)

	def apply(): Routes = new Routes {
		val routes = Map[String, HttpRoute]()
	}
}
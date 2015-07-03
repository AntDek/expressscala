package expressscala.http

import scala.concurrent._
import ExecutionContext.Implicits.global

trait Listener {
	def listen(path: String)(handler: Request => Future[Response]): Unit
	def start: Subscription
}
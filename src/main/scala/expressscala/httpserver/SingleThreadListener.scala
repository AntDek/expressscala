package expressscala.httpserver

import expressscala.http._
import expressscala.errorResponse
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.InetSocketAddress

trait SingleThreadListener extends Listener {
	val server: HttpServer

	def listen(path: String)(handler: Request => Future[Response]) = {
		createExchange(path) { exchange: Exchange =>
			handler(exchange.request).onComplete {
				case Success(res: Response) => exchange.sendResponse(res)
				case Failure(t) => exchange.sendResponse(errorResponse("An error has occured: " + t.getMessage))
			}
		}
	}

	def start = {
		server.start
		new Subscription {
			def unsubscribe: Unit = {
				println("unsubscribed")
			}
		}
	}

	private def createExchange(path: String)(handler: Exchange => Unit): Unit = {
		server.createContext(path, new HttpHandler {
			def handle(exchange: HttpExchange): Unit = handler(Exchange(exchange))
		})
	}
}

object SingleThreadListener {
	def apply(port: Int): SingleThreadListener = new SingleThreadListener {
		val server = HttpServer.create(new InetSocketAddress(port), 0)
	}
}
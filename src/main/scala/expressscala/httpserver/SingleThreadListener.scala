package expressscala.httpserver

import expressscala.http._
import expressscala.errorResponse
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.InetSocketAddress
import java.util.concurrent.{Executor, ThreadPoolExecutor, TimeUnit, LinkedBlockingQueue}

trait SingleThreadListener extends Listener {
	val server: HttpServer
	val executor: ThreadPoolExecutor

	def listen(path: String)(handler: Request => Response): Unit = {
		createExchange(path) { exchange: Exchange =>
			runContext(exchange.request, handler) onComplete {
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

	private def runContext(request: Request, handler: Request => Response) = Future {
		handler(request)
	}

}

object SingleThreadListener {
	def apply(port: Int): SingleThreadListener = new SingleThreadListener {
		val server = HttpServer.create(new InetSocketAddress(port), 0)
		val executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue)
		server.setExecutor(executor)
	}
}
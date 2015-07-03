package expressscala.httpserver

import expressscala.http._
import com.sun.net.httpserver.HttpExchange

trait Exchange {
	
	val exchange: HttpExchange
	
	val request: Request

	def sendResponse(response: Response): Unit = {
		val stream = exchange.getResponseBody

		val headers = exchange.getResponseHeaders
		response.headers.foreach { case (key, value) =>
			headers.set(key, value)
		}

		exchange.sendResponseHeaders(response.code, 0L)
		response.data.foreach { v =>
			stream.write(v)
		}

		stream.close
	}
}

object Exchange {
	def apply(exch: HttpExchange) = new Exchange {
		val exchange = exch
		val request: Request = HttpRequestDecoder(exchange).getRequest
	}


}
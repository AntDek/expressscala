package expressscala.httpserver

import expressscala.http._
import scala.collection.JavaConversions._
import com.sun.net.httpserver.HttpExchange


trait RequestDecoder {
	def getRequest: Request
}

object HttpRequestDecoder {
	def apply(exchange: HttpExchange): RequestDecoder = new RequestDecoder {
		def getRequest = new Request {
			val h = for ((k, vs) <- exchange.getRequestHeaders) yield (k, vs.toList)
			val headers = Map() ++ h
			val method = exchange.getRequestMethod
			val path = exchange.getRequestURI.getPath
			// val query = httpExchange.getRequestURI.getQuery
		}
	}
}
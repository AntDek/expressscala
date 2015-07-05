package expressscala.httpserver

import expressscala.http._
import scala.collection.JavaConversions._
import com.sun.net.httpserver.HttpExchange
import scala.io.Source


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
			lazy val query = queryToMap(exchange.getRequestURI.getQuery)
			lazy val body = Source.fromInputStream(exchange.getRequestBody)
		}
	}

	private def queryToMap(query: String): Map[String, String] = {
		query.split('&').foldLeft(Map[String, String]()) ((m: Map[String, String], v: String) => {
			val pair = v.split('=')
			if (pair.length != 2) {
				m
			} else {
				m + (pair(0) -> pair(1))
			}
		})
	}
}
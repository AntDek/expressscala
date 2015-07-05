package expressscala.http

import scala.io.Source

trait Request {
	val method: String
	val headers: Map[String, List[String]]
	val path: String
	val query: Map[String, String]
	val body: Source
}
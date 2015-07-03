package expressscala.http

trait Request {
	val method: String
	val headers: Map[String, List[String]]
	val path: String
	// val query: Map[String, String]
	// def data: Itarable[Bytes]
}
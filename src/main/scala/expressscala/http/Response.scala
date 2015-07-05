package expressscala.http

trait Response {
	val code: Int
	val headers: Map[String, String]
	val data: Iterator[Byte]
}

object Response {
	def apply(c: Int, h: Map[String, String], d: String): Response = {
		new Response {
			val code = c
			val headers = h
			val data = d.getBytes.iterator
		}
	}
}
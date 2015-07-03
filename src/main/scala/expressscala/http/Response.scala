package expressscala.http

// trait IResponse {
// 	def code: Int
// 	def headers: Map[String, String]
// 	def data: Iterator[Bytes]
// }

case class Response(code: Int, headers: Map[String, String], data: Iterator[Byte])

object Response {
	def apply(code: Int, headers: Map[String, String], data: String): Response = {
		new Response(code, headers, data.getBytes.iterator)
	}
}
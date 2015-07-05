import expressscala.http._
import scala.io.Source

package object expressscala {

	import org.json4s._
	import org.json4s.jackson.Serialization
	import org.json4s.jackson.Serialization.write
	import org.json4s.jackson.JsonMethods._
	implicit val sformats = Serialization.formats(NoTypeHints)
	implicit val formats = DefaultFormats

	def textResponse(data: String) = {
		successResponse(Map("Content-Type" -> "text/html"), data)
	}

	def successResponse(headers: Map[String, String], data: String) = {
		Response(200, headers, data)
	}

	def notFoundResponse(data: String) = {
		Response(403, Map("Content-Type" -> "text/html"), data)
	}

	def errorResponse(data: String) = {
		Response(500, Map("Content-Type" -> "text/html"), data)
	}

	def jsonResponse[T <: AnyRef](data: T) = {
		successResponse(Map("Content-Type" -> "application/json"), write(data))
	}

	implicit class SourceToJson(val s: Source) extends AnyVal {
		def extract[T](implicit m: Manifest[T]): T = {
			parse(s.mkString).extract[T]
		}
	}
}
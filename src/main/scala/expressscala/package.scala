// import scala.concurrent._
// import ExecutionContext.Implicits.global

import expressscala.http._
import scala.io.Source

package object expressscala {

	// def asyncTextResponse(handler: Unit => Future[String]): Future[Response] = {
	// 	handler onComplete {
	// 		case Success(data) => textResponse(Map.empty, data)
	// 		case Failure(t) => errorResponse("An error has occured: " + t.getMessage)
	// 	}
	// }

	// def asyncTextResponse[A](headers:Map[String, String])(handler: Unit => Future[A]): Future[Response] = {

	// }

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

	import org.json4s._
	import org.json4s.jackson.Serialization
	import org.json4s.jackson.Serialization.write
	implicit val sformats = Serialization.formats(NoTypeHints)

	def jsonResponse[T <: AnyRef](data: T) = {
		successResponse(Map("Content-Type" -> "application/json"), write(data))
	}

	import org.json4s.jackson.JsonMethods._
	implicit val formats = DefaultFormats
	implicit class SourceToJson(val s: Source) extends AnyVal {
		def extract[T](implicit m: Manifest[T]): T = {
			parse(s.mkString).extract[T]
		}
	}

	// implicit def f2ops(f: Source.type) = new SourceToJson(f)


}
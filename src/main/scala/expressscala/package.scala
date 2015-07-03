// import scala.concurrent._
// import ExecutionContext.Implicits.global

import expressscala.http._

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


}
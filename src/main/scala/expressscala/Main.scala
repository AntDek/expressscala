package expressscala

import expressscala.http._

object Main {

	var index = 0

	def main(args: Array[String]): Unit = {
		val app = Express(8081)

		app.get("/ahoj") { r: Request =>
			index = index + 1
			val inner = index
			println("run" + inner)
			textResponse("Yahoo!!! " + inner)
		}

		app.start
	}
	
}
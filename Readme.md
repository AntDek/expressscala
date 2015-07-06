ExpressScala 
============
is a simple asynchronous HTTP Server. The HTTP server will asynchronously wait on some port for incoming HTTP requests and then respond to them by sending some text, HTML or JSON back.

About ExpressScala
------------------
The framework is developed as an extension of the assignment of the course [Principles of Reactive Programming](https://www.coursera.org/course/reactive). The idea is to research how asynchronous server can work in scala, handling http requests and json data. Also to use the framework should be as simple as possible.

Examples
--------
**Hello world**

```scala
import expressscala._

val app = Express(8081)

app.get("/") { r =>
  textResponse("Hello World")
}

app.start
```

**handling json obejcts**
```scala
import expressscala._

case class User(val name: String, val email: String)

val app = Express(8081)

app.post("/user") { r =>
  val user = r.body.extract[User]
  textResponse("Resived data for user name: " + user.name)
}

app.get("/user") { r =>
  val user = User("Demo User", "demo.user@example.com")
  jsonResponse[User](user)
}

app.start
```

Tests
-----
```scala
sbt test
```

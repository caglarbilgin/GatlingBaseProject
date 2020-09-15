package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt // to use milliseconds

class CheckResponseCode extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
  //.proxy(Proxy("localhost",8888)) // to see response body via progress telerik fiddler

  val scn = scenario("Video Games DB - 3 scenario") //Generic scenario name

    .exec(http("Get All Video Games - 1st call") // first scenario name
    .get("videogames")
  .check(status.is(200))) // check the status is ok
    .pause(5)

    .exec(http("Get specific game")
      .get("videogames/1")
    .check(status.in(200 to 210))) // check the status is the between 200 and 210 ?
    .pause(1, 20)

    .exec(http("Get All Video Games - 2nd call")
      .get("videogames")
    .check(status.not(404), status.not(500))) // check the status is not 404 and 500
    .pause(3000.milliseconds)


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}


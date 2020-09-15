package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt // to use milliseconds

class AddPauseTime extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
    //.proxy(Proxy("localhost",8888)) // to see response body via progress telerik fiddler

  val scn = scenario("Video Games DB - 3 scenario") //Generic scenario name

    .exec(http("Get All Video Games - 1st call") // first scenario name
      .get("videogames")) // get videogames path
    .pause(5)

    .exec(http("Get specific game")
      .get("videogames/1"))
    .pause(1, 20)

    .exec(http("Get All Video Games - 2nd call")
    .get("videogames"))
    .pause(3000.milliseconds)


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}

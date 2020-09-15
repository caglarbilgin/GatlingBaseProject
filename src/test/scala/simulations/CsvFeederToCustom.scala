package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeederToCustom extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  var idNumbers = (1 to 10).iterator
  println(idNumbers)
  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

  def getSpecificVideoGame() = {
    repeat(4) {
      feed(customFeeder)
        .exec(http("Get specific video game")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(2)

    }
  }

  val scn = scenario("Get specific video games from csv")
    .exec(getSpecificVideoGame())


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}

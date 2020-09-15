package simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends Simulation {
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val csvFeeder = csv("data/gameCsvFile.csv").circular
  //circular means when the cvs file comes end of the file, it will come top of the file until the test is done.(like loop)
  //You have more than 1 options like random, queue and circular.

  def getSpecificVideoGame() = {
    repeat(5) {
      feed(csvFeeder)
        .exec(http("Get specific video game")
          .get("videogames/${gameId}")
          .check(jsonPath("$.name").is("${gameName}"))
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

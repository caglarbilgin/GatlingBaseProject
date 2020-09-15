package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class FixedDurationLoadSimulation extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")


  def getAllVideoGames() = {
    exec(http("get all video games")
      .get("videogames")
      .check(status.is(200)))

  }

  def getSpecificVideoGame() = {

    exec(http("get specific video game")
      .get("videogames/1")
      .check(status.is(200)))
  }

  val scn = scenario("Fixed Duration Load Simulation ")
    .forever() {
      exec(getAllVideoGames()
        .pause(5))
        .exec(getSpecificVideoGame()
          .pause(5))
        .exec(getAllVideoGames())

    }


  setUp(
    scn.inject(
      nothingFor(5 seconds), //do nothing for 5 sec
      atOnceUsers(10), //start 10 users
      rampUsers(50) during(30 seconds) // add 50 users in 30 seconds
    ).protocols(httpConf.inferHtmlResources())
  ).maxDuration(1 minute) // finish the test after 1 minute

}

package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RampUsersLoadSimulation extends Simulation {


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

  val scn = scenario("Basic Load Simulation ")
    .exec(getAllVideoGames()
      .pause(5))
    .exec(getSpecificVideoGame()
      .pause(5))
    .exec(getAllVideoGames())



  setUp(
    scn.inject(
      nothingFor(5 seconds),
      //constantUsersPerSec(10) during(10 seconds)
      rampUsersPerSec(1) to (5) during(20 seconds)
    ).protocols(httpConf.inferHtmlResources())
  )

}

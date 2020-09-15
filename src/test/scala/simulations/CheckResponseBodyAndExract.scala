package simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExract extends Simulation {
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Check JSON Path")
    .exec(http("Get specific game")
      .get("videogames/1")
      .check(jsonPath("$.name").is("Resident Evil 4")))


    .exec(http("Get All Video Games")
      .get("videogames")
      .check(jsonPath("$[1].id").saveAs("gameId")))

    .exec { session => println(session); session } // to debug

    .exec(http("Get Specific Video Game")
    .get("videogames/${gameId}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responseBody")))

    .exec { session => println("Response Body: " + session("responseBody").as[String]); session } // https://www.james-willett.com/debug-gatling


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}

package simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuseWithObjects extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
  //.proxy(Proxy("localhost",8888)) // to see response body via progress telerik fiddler
/*
  val scn = scenario("Video Games DB - 3 scenario") //Generic scenario name

    .exec(http("Get All Video Games - 1st call") // first scenario name
    .get("videogames")) // get videogames path


    .exec(http("Get specific game")
    .get("videogames/1"))


    .exec(http("Get All Video Games - 2nd call")
      .get("videogames"))
*/
  def getAllVideoGames() = { // create a function and use it in the scenario
   repeat(10){ // repeat 10 times in this code
     exec(http("Get All Video Games - 1st call") // first scenario name
       .get("videogames")
       .check(status.is(200)))
   }

  }

  val scn = scenario("code reuse")
    .exec(getAllVideoGames)
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}

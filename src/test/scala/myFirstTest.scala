import io.gatling.core.Predef._
import io.gatling.http.Predef._

class myFirstTest extends Simulation {


  //header definition
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")
    //.proxy(Proxy("localhost",8888))

  //scenario definition
  val scn = scenario("My First Load Test")
    .exec(http("Get All Video Games")
      .get("videogames"))

  //load test scenario
  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpConf)
  )

}
package controllers

import com.nicta.rng.Rng
import io.plasmap.Participant
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.oauth._
import play.api.mvc._
import upickle.default._


import scala.concurrent.Future


object Application extends Controller {

  def index() = Action.async {
    request => Future {
      Ok(views.html.index())

    }
  }

  def findWinner() = Action.async {
    request => Future {
      request.body.asText.map(upickle.default.read[List[Participant]])
      Ok("Meatball")
    }
  }
}

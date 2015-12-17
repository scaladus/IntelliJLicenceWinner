package io.plasmap.components

import io.plasmap.Participant
import japgolly.scalajs.react.{CallbackTo, Callback}
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.raw.XMLHttpRequest
import scalaz.syntax.id._
import upickle.default.read
import upickle.default.write
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object FromServer {

  private def post[I, O](serialise: I => String, deserialise: String => O)
                        (url:String, input:I, callback: O => Callback):Callback = {
    val fut = Ajax
      .post(url, serialise(input))
      .map(_.responseText |> deserialise.andThen(callback) )
    (CallbackTo future fut).void
  }

  def findWinner(participants:List[Participant], callback: ((Map[String, String],String)) => Callback) = {
    post(write[String], read[(Map[String, String],String)])(
      "/getosmdata", id, callback
    )
  }

}

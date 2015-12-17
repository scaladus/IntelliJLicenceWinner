package io.plasmap.components

import io.plasmap.Participant
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.ScalazReact._
import org.scalajs.dom.ext.Ajax

/**
 * Created by erna on 11/3/15.
 */
object MainComponent {

  case class State(participants:List[Participant], winner:Option[Participant])

  val component = ReactComponentB[Unit]("Winner Component")
    .initialState(State(List(Participant("Marius"), Participant("Jan"), Participant("Mark")), None))
    .render( $ => {

      $.state.winner.map(
        winner => <.h1(s"Our winner is ${winner.name}"):ReactElement
      ).getOrElse(
        <.div(
          for( (p, index) <- $.state.participants.zipWithIndex) yield {
            <.input(
              ^.`type` := "text",
              ^.value  := p.name,
              ^.onChange ==> ((e:ReactEventI) => {
                $.modState(s => {
                  val ps = s.participants
                  val newPs = ps.updated(index, Participant(e.target.value))
                  s.copy(participants =  newPs)
                  })
                }
              )
          )
          },
          <.div(
            <.button(
              ^.onClick --> $.modState(s => s.copy(participants = s.participants :+ Participant("")))
            )("More!")
          ),
          <.br(),
          <.button(
            ^.onClick -->
              FromServer.findWinner(
                $.state.participants,
                (p) => $.modState(_.copy(winner = Some(p)))
          )
        )("Determine the winner!")
      )
    )

  }).buildU

}

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
    .initialState(State(List(
      "Axel Poigné",
      "Teresa Rütten",
      "David",
      "Roland Manfrahs",
      "Endre Zonai",
      "Dhia Abbassi",
      "Oliver Lüttin",
      "Ankur Srivastava",
      "koenighotze",
      "Kevin Shale",
      "Michael S.",
      "Mobe",
      "Pawel M.",
      "Luis Delgado Romera",
      "Valentin Willscher",
      "Robert Giacinto",
      "Gunther Wittig",
      "Torsten Schmidt",
      "Matthias Rütten",
      "Marius Soutier",
      "Erna",
      "Jan",
      "Mark"
    ).map(Participant), None))
    .render( $ => {
      $.state.winner.map( winner =>
        <.div( ^.cls := "row")(
          <.div( ^.cls := "small-10 columns")(
             <.h1(s"Our winner is ${winner.name}")
          ),
          <.div( ^.cls := "small-2 columns")(
            <.button( ^.color := "white", ^.onClick --> $.modState(_.copy(winner = None)))("Back")
          )
        ):ReactElement
      ).getOrElse(
        <.div(
          <.div( ^.cls := "row")(<.h1("Who wants to win an IntelliJ licence?")),
          <.div( ^.cls := "row")(
              for( (p, index) <- $.state.participants.zipWithIndex) yield {
                <.div( ^.cls := "row collapse postfix-round")(
                  <.div( ^.cls := "small-11 columns")(
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
                  )),
                  <.div( ^.cls := "small-1 columns")(
                    <.button( ^.color := "white", ^.fontSize := "18pt", ^.onClick --> $.modState(s => {
                      val newParticipants = s.participants.zipWithIndex.filter{ case (p, i) => index != i }.map(_._1)
                      s.copy(participants = newParticipants)
                    }),
                      ^.cls := "secondary postfix"
                  )("☠")
                  )
                )}
          ),
            <.div( ^.cls := "row")(
              <.div( ^.cls := "small-2 columns")(
                <.button(
                  ^.color := "white",
                  ^.onClick --> $.modState(s => s.copy(participants = s.participants :+ Participant("")))
                )("More!")
              )
            ,
              <.div( ^.cls := "small-5 small-offset-5 columns")(
                <.button(^.color := "white", ^.cls := "prefix-round postfix-round")(
                  ^.onClick -->
                    FromServer.findWinner(
                      $.state.participants,
                      (p) => $.modState(_.copy(winner = Some(p)))
                )
              )("Determine the winner!")
            )
          )
        )
    )

  }).buildU

}

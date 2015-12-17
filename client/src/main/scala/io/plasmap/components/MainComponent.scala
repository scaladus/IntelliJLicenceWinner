package io.plasmap.components

import io.plasmap.Participant
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.ScalazReact._

/**
 * Created by erna on 11/3/15.
 */
object MainComponent {

  val component = ReactComponentB[Unit]("Winner Component")
    .initialState(List.empty[Participant])
    .renderPS((scope, props, state) => {

    Foundation.basic(
      <.form(

      ),
      <.button(
        ^.onClick --> Ajax.
      )
    )

  }).buildU

}

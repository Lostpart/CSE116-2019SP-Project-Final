package gui

import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}

import main.World_C
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextField}

import scala.io.Source

class button(gameworld: World_C,usernametextfield:TextField, xScale: Double, yScale: Double) extends Button {

  minWidth = 100 * xScale
  minHeight = 100 * yScale
  style = "-fx-font: 12 ariel;"

}

class startgame(gameworld: World_C,usernametextfield:TextField, xScale: Double = 1.0, yScale: Double = 1.0) extends button(gameworld,usernametextfield, xScale, yScale) {
  text = "Start Game"
  style = "-fx-font: 24 ariel;"
  onAction = (event: ActionEvent) => {
    gameworld.username = usernametextfield.text.value
    println(gameworld.username.toString +"准备登录")
    network.webSocketSender.joingameSend(gameworld)
  }
}

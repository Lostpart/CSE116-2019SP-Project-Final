package control

import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import main.Player_C


class ingame(player: Player_C) extends EventHandler[KeyEvent] {


  override def handle(event: KeyEvent): Unit = {
    val keyCode = event.getCode
    event.getEventType.getName match {
      case "KEY_RELEASED" => keyCode.getName match {
        case "Left" => player.moveX(0)
        case "Right" => player.moveX(0)
        case "Up" => player.moveY(0)
        case "Down" => player.moveY(0)
        case _ =>
      }
      case "KEY_PRESSED" => keyCode.getName match {
        case "Left" => player.moveX(-1)
        case "Right" => player.moveX(1)
        case "Up" => player.moveY(-1)
        case "Down" => player.moveY(1)
        case _ =>
      }
      case _ =>
    }
  }

}

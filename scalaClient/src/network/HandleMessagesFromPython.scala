package network

import control.ingame
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.scene.input.KeyEvent
import play.api.libs.json.{JsValue, Json}
import main.{Item_C, Player_C, World_C}
import main.main.{CalCenter, drawCircle, drawCircle_withname}
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{Alert, TextField}
import scalafx.scene.{Group, Scene}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}


class HandleMessagesFromPython(gameworld:World_C, GuiWindow:JFXApp) extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val rawdata: String = objects.apply(0).toString
    val js_parsed: JsValue = Json.parse(rawdata)
    val msgtype: String = (js_parsed \ "msgtype").as[String]
    println("收到了一个I recived a "+msgtype)
    //此处进行数据包初步判断和分发
    //Preliminary judgment and distribution data packets here

    msgtype match {

      case "heartbeat" =>

        heartbeatReceive(gameworld,false,rawdata)

      case "joingame" =>
        joingameReceive(gameworld, rawdata)

      case "leavegame" =>
        leavegameReceive(gameworld, rawdata)
      case "testmsg" => println(rawdata)
    }
//    println("处理完了一个消息")
  }

  def JSplayer_to_PlayerC(input: JsValue):Player_C={
    val username:String = input(0).as[String]
    val player:Player_C= new Player_C(username)
    //[x, y, resource, size, color, speed]

    val listOfSub:Array[Array[Double]]= input(1).as[Array[Array[Double]]]

    for(s <- listOfSub.indices){

      var lcl_sub:Item_C = new Item_C(listOfSub(s)(0),listOfSub(s)(1),listOfSub(s)(3),listOfSub(s)(4))
      lcl_sub.resource = listOfSub(s)(2).toInt
      lcl_sub.speed = listOfSub(s)(5)
      lcl_sub.name = username
      lcl_sub.namet = "player_sub"
      player.sub = player.sub :+ lcl_sub
    }
    player
  }
  def JSplayer_to_PlayerC_updatesizeonly(input: JsValue,gameworld:World_C):Unit={
    //[x, y, resource, size, color, speed]

    val listOfSub:Array[Array[Double]]= input(1).as[Array[Array[Double]]]

    for(s <- listOfSub.indices){
      gameworld.user.sub(s).resource = listOfSub(s)(2).toInt
      gameworld.user.sub(s).size = listOfSub(s)(3)

    }

  }
  def heartbeatReceive(gameworld:World_C,firstHeartbeat:Boolean,result_str:String):Unit={
    //此处开始处理发回的JSON
    val js_parsed: JsValue = Json.parse(result_str)
    val state: Int = (js_parsed \ "state").as[Int]

    if (firstHeartbeat) {
      //此处更新 刺 的位置
      heartbeat_update_thorn((js_parsed \ "thorn").as[JsValue], gameworld)
    }

    //      200- Alive(Good)
    //      501- Server has more player_sub then Client, pls check
    //      503- Server has less player_sub then client, pls check
    //      301- Player die
    //      302- Whole Game are ended
    //      403- Invalid token

    state match {
      case 200 => {
        //仅更新 玩家size 其它玩家 资源球 泡泡 的位置

        heartbeat_update_user_sizeonly((js_parsed \ "player").as[JsValue], gameworld)
        heartbeat_update_otherplayer((js_parsed \ "otherplayer").as[JsValue], gameworld)
        heartbeat_update_resourceball((js_parsed \ "resourceball").as[JsValue], gameworld)
        heartbeat_update_bubble((js_parsed \ "bubble").as[JsValue], gameworld)
      }
      case 501 => {
        println("Reminder 501 Server has more player_sub then Client, pls check")
        //仅更新 玩家 其它玩家 资源球 泡泡 的位置

        heartbeat_update_user((js_parsed \ "player").as[JsValue], gameworld)
        heartbeat_update_otherplayer((js_parsed \ "otherplayer").as[JsValue], gameworld)
        heartbeat_update_resourceball((js_parsed \ "resourceball").as[JsValue], gameworld)
        heartbeat_update_bubble((js_parsed \ "bubble").as[JsValue], gameworld)
      }
      case 503 => {
        println("Reminder 503 Server has less player_sub then client, pls check")
        //仅更新 玩家 其它玩家 资源球 泡泡 的位置

        heartbeat_update_user((js_parsed \ "player").as[JsValue], gameworld)
        heartbeat_update_otherplayer((js_parsed \ "otherplayer").as[JsValue], gameworld)
        heartbeat_update_resourceball((js_parsed \ "resourceball").as[JsValue], gameworld)
        heartbeat_update_bubble((js_parsed \ "bubble").as[JsValue], gameworld)
      }
      case 301 => {
        println("Reminder 301 Player die")
        //发送结束游戏数据包，提醒玩家已死亡
        network.webSocketSender.leavegameSend(gameworld)
        gameworld.endtime_Timestamp = main.timeUpdate.getCurrent_time()
      }
      case 302 => {
        println("Reminder 302 Whole Game are ended")
        //提醒玩家游戏已结束
      }
      case 403 => println("Error 403 Invalid token")
    }

    def heartbeat_update_user(input: JsValue, gameworld:World_C):Unit={
      //["Shiluo",[[763,769,12,2,3,51],[763,769,12,2,3,51]]]
      gameworld.user = JSplayer_to_PlayerC(input)
    }
    def heartbeat_update_user_sizeonly(input: JsValue, gameworld:World_C):Unit={
      //["Shiluo",[[763,769,12,2,3,51],[763,769,12,2,3,51]]]
      JSplayer_to_PlayerC_updatesizeonly(input,gameworld)
    }
    def heartbeat_update_otherplayer(input: JsValue, gameworld:World_C):Unit={
      val input_arr:Array[JsValue] = input.as[Array[JsValue]]
      var lcl_Array:ArrayBuffer[Player_C] = new ArrayBuffer[Player_C]
      for(p <- input_arr){
        lcl_Array +=  JSplayer_to_PlayerC(p)
      }
      gameworld.player = lcl_Array.toArray
    }
    def heartbeat_update_resourceball(input: JsValue, gameworld:World_C):Unit={
      val input_arr:Array[Array[Double]] = input.as[Array[Array[Double]]]
      var lcl_Array:ArrayBuffer[Item_C] = new ArrayBuffer[Item_C]
      for(r <- input_arr){
        lcl_Array +=  new Item_C(r(0),r(1),0.5, r(2))
      }
      gameworld.resourceball = lcl_Array.toArray
    }
    def heartbeat_update_bubble(input: JsValue, gameworld:World_C):Unit={
      val input_arr:Array[Array[Double]] = input.as[Array[Array[Double]]]
      var lcl_Array:ArrayBuffer[Item_C] = new ArrayBuffer[Item_C]
      for(r <- input_arr){
        lcl_Array +=  new Item_C(r(0),r(1),r(2),r(3))
      }
      gameworld.bobble = lcl_Array.toArray
    }
    def heartbeat_update_thorn(input: JsValue, gameworld:World_C):Unit={
      val input_arr:Array[Array[Double]] = input.as[Array[Array[Double]]]
      var lcl_Array:ArrayBuffer[Item_C] = new ArrayBuffer[Item_C]
      for(r <- input_arr){
        lcl_Array +=  new Item_C(r(0),r(1),r(2),8)
      }
      gameworld.thorn = lcl_Array.toArray
    }
  }

  def joingameReceive(gameworld:World_C, result_str:String):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “joingame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */

    val js_parsed: JsValue = Json.parse(result_str)
    val state: Int = (js_parsed \ "state").as[Int]

    //401 this name has already been used
    //301 empty username
    //200 join success

    state match {
      case 200 => {
        if((js_parsed \ "username").as[String] == gameworld.username && (js_parsed \ "msgtype").as[String] == "joingame" ){
          gameworld.Boundary = (js_parsed \ "boundary").as[Array[Int]]
          gameworld.user = JSplayer_to_PlayerC((js_parsed \ "player").as[JsArray])
          gameworld.endtime_Timestamp = (js_parsed \ "endtime").as[Long]
          gameworld.token = (js_parsed \ "token").as[String]
          gameworld.loginsuccess = true
          println(gameworld.token)

//          GUIwindow.stage = new PrimaryStage {
//            this.title = "CSE116 Personal Project Demo 3"
//            this.scene = new Scene(windowWidth, windowHeight) {
//              content = List(sceneGraphics)
//              addEventHandler(KeyEvent.ANY, new ingame(gameworld.user))
//            }
//            val update: Long => Unit = (time: Long) => {
//              val delta:Long = (time - gl_lastGUIupdate)/1000000
//              gameworld.update(delta)
//              gl_lastGUIupdate = time
//              sceneGraphics.children.clear()
//              var lcl_data:Array[Array[Double]] = CalCenter(gameworld.user,windowWidth,windowHeight)
//              var lcl_Scaling: Double = math.max(0.05,math.max(lcl_data(0)(0),lcl_data(0)(1)))
//              var lcl_topLeft_coordinate: Array[Double] = Array(lcl_data(1)(0)-(windowWidth/2)*lcl_Scaling,lcl_data(1)(1)-(windowHeight/2)*lcl_Scaling)
//              for (r <- gameworld.resourceball) {drawCircle(r.x,r.y,r.size,r.color,lcl_topLeft_coordinate,lcl_Scaling)}
//              for (r <- gameworld.bobble) {drawCircle(r.x,r.y,r.size,r.color,lcl_topLeft_coordinate,lcl_Scaling)}
//              for(p <- gameworld.player){for (r <- p.sub) {drawCircle_withname(r.x,r.y,r.size,r.color,p.name,lcl_topLeft_coordinate,lcl_Scaling)}}
//              for (r <- gameworld.user.sub) {drawCircle_withname(r.x,r.y,r.size,r.color,r.name,lcl_topLeft_coordinate,lcl_Scaling)}
//            }
//            AnimationTimer(update).start()
//          }
        } else {
          println("Error 000 username/msgtype doesn't match with sending username/msgtype")
        }

      }
      case 301 => println("Error 301 empty username")
      case 401 => println("Error 401 this name has already been used")
    }

  }


  def leavegameReceive(gameworld:World_C, result_str:String):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “leavegame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */
    //{"state":200,"username":"Shiluo","msgtype":"leavegame","player":["Shiluo",[[763,769,12,2,3,51]]]}

    val js_parsed: JsValue = Json.parse(result_str)
    val state: Int = (js_parsed \ "state").as[Int]

    //401 this name has already been used
    //301 empty username
    //200 join success

    state match {
      case 200 => {
        println("200 the player die")
//        val information = new Alert(Alert.AlertType.Information, "You Die! The game is going to close.")
//        information.setTitle("information")
//        information.setHeaderText("Information")
//        information.showAndWait
//        GuiWindow.stage.close()
        gameworld.playerdie = true
      }
      case 301 => println("Error 301 empty username")
      case 407 => println("Error 407 this name is not in game")
    }
  }




}

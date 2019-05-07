package network

import java.io.{InputStream, OutputStream}
import java.net.Socket

import main.{Item_C, Player_C, World_C}
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.collection.mutable.{ArrayBuffer, ListBuffer} // Combinator syntax


object network_socket {
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
  def heartbeat(gameworld:World_C,firstHeartbeat:Boolean):Unit={
    /**
      * /Heartbeat
      * Parameter	    Type	                        Description & e.g
      * msgtype	      String	                      “heartbeat”(for Demo2 only)
      * username	    String	                      Username e.g:Shiluo
      * token	        String	                      40 character token generate when user login
      *                                              E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      * time	        Int	10 digit                  Timestamp
      * location	    List of List with two Double	The new location for each player_sub
      *                                             [[1.0,5.2],[4.1,3.3],[2.1,1.1]]
      * action	      List of Action	              Check Action part
      */

    //action部分
    var action:Map[String,Array[Double]] = Map()
    if(gameworld.user.action_split){
      // Not in Demo2
    }

    if(gameworld.user.action_bobble){
      // Not in Demo2
    }


    //把所有的位置信息转为Json
    var location_buf:ArrayBuffer[Array[Double]]= new ArrayBuffer[Array[Double]]
    for(s <- gameworld.user.sub){
      location_buf += Array(s.x,s.y)
    }
    var location:Array[Array[Double]]=location_buf.toArray


    val jsonMap: Map[String, JsValue] = Map(
      "username" -> Json.toJson(gameworld.user.name),
      "msgtype" -> Json.toJson("heartbeat"),
      "time" -> Json.toJson(main.timeUpdate.getCurrent_time()),
      "location" -> Json.toJson(location),
      "action" -> Json.toJson(action))
    try {
      var result_str = socket_send(gameworld.sc_ip, gameworld.sc_port, Json.stringify(Json.toJson(jsonMap)))

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
          leavegame(gameworld, gameworld.user.name)
          gameworld.endtime_Timestamp = main.timeUpdate.getCurrent_time()
        }
        case 302 => {
          println("Reminder 302 Whole Game are ended")
          //提醒玩家游戏已结束
        }
        case 403 => println("Error 403 Invalid token")
      }
    }catch {
      case ex: com.fasterxml.jackson.core.io.JsonEOFException => println("Heartbeat received an incomplete packet, ignored")
    }

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

  def joingame(gameworld:World_C,username:String):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “joingame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */
    val jsonMap: Map[String, JsValue] = Map(
    "username" -> Json.toJson(username),
    "msgtype" -> Json.toJson("joingame"))
    val result_str:String = socket_send(gameworld.sc_ip,gameworld.sc_port,Json.stringify(Json.toJson(jsonMap)))
    //{"state":200,"username":"Shiluo","msgtype":"joingame","boundary":[1000,1000],"player":["Shiluo",[[763,769,12,2,3,51]]]}


    val js_parsed: JsValue = Json.parse(result_str)
    val state: Int = (js_parsed \ "state").as[Int]

    //401 this name has already been used
    //301 empty username
    //200 join success

    state match {
      case 200 => {
        if((js_parsed \ "username").as[String] == username && (js_parsed \ "msgtype").as[String] == "joingame" ){
          gameworld.Boundary = (js_parsed \ "boundary").as[Array[Int]]
          gameworld.user = JSplayer_to_PlayerC((js_parsed \ "player").as[JsArray])
          gameworld.endtime_Timestamp = (js_parsed \ "endtime").as[Long]
        } else {
          println("Error 000 username/msgtype doesn't match with sending username/msgtype")
        }

      }
      case 301 => println("Error 301 empty username")
      case 401 => println("Error 401 this name has already been used")
    }
  }

  def leavegame(gameworld:World_C,username:String):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “leavegame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */
    val jsonMap: Map[String, JsValue] = Map(
      "username" -> Json.toJson(username),
      "msgtype" -> Json.toJson("leavegame"))
    val result_str:String = socket_send(gameworld.sc_ip,gameworld.sc_port,Json.stringify(Json.toJson(jsonMap)))
    //{"state":200,"username":"Shiluo","msgtype":"leavegame","player":["Shiluo",[[763,769,12,2,3,51]]]}


    val js_parsed: JsValue = Json.parse(result_str)
    val state: Int = (js_parsed \ "state").as[Int]

    //401 this name has already been used
    //301 empty username
    //200 join success

    state match {
      case 200 => {}
      case 301 => println("Error 301 empty username")
      case 407 => println("Error 407 this name is not in game")
    }
  }

def socket_send(ip:String,port:Int,msg:String):String={

  val sc:Socket = new Socket(ip,port)
  val input:InputStream = sc.getInputStream
  val output:OutputStream = sc.getOutputStream

  //利用output流 Use output flow (work similar as writing file)
  output.write(msg.getBytes())

  // get first 1024 Byte data (which usually is more then enough, but you can always change the size)
  // work similar as reading file
  val result:Array[Byte]=new Array[Byte](409600)
  val result_len:Int = input.read(result)
  val result_str:String = new String(result,0,result_len)
  //println(result_str)

  //remember to close connect
  input.close()
  output.close()
  sc.close()

  result_str
}
}

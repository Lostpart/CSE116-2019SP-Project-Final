package network

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import main.{Item_C, Player_C, World_C}
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}


object webSocketSender {

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
  def JSplayer_to_PlayerC_updatesizeonly(input: JsValue,gameworld:World_C):Unit= {
    //[x, y, resource, size, color, speed]

    val listOfSub: Array[Array[Double]] = input(1).as[Array[Array[Double]]]

    for (s <- listOfSub.indices) {
      gameworld.user.sub(s).resource = listOfSub(s)(2).toInt
      gameworld.user.sub(s).size = listOfSub(s)(3)

    }
  }

  def heartbeatSend(gameworld:World_C,socket:Socket):Unit={
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
      */
    //val socket:Socket = gameworld.socket
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
      "action" -> Json.toJson(action),
      "token" -> Json.toJson(gameworld.token))

    socket.emit("heartbeat", Json.stringify(Json.toJson(jsonMap)))
  }


  def joingameSend(gameworld:World_C):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “joingame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */
    val socket:Socket = gameworld.socket
    val jsonMap: Map[String, JsValue] = Map(
      "username" -> Json.toJson(gameworld.username),
      "msgtype" -> Json.toJson("joingame"),
      "token" -> Json.toJson(gameworld.token))
    socket.emit("joingame",Json.stringify(Json.toJson(jsonMap)))
    //{"state":200,"username":"Shiluo","msgtype":"joingame","boundary":[1000,1000],"player":["Shiluo",[[763,769,12,2,3,51]]]}

  }


  def leavegameSend(gameworld:World_C):Unit={
    /**
    Parameter         Type          Description & e.g
    msgtype           String        “leavegame”(for Demo2 only)
    username          String        Username e.g:Shiluo
    token             String        40 character token generate when user login
                                    E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
      */
    val socket:Socket = gameworld.socket
    val jsonMap: Map[String, JsValue] = Map(
      "username" -> Json.toJson(gameworld.username),
      "msgtype" -> Json.toJson("leavegame"),
      "token" -> Json.toJson(gameworld.token))
    socket.emit("leavegame",Json.stringify(Json.toJson(jsonMap)))
    //{"state":200,"username":"Shiluo","msgtype":"leavegame","player":["Shiluo",[[763,769,12,2,3,51]]]}
  }
}

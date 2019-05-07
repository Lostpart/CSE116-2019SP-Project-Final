package actor_socket

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import objects.{Player, World}
import play.api.libs.json.{JsValue, Json}

import scala.util.control.Breaks.{break, breakable}

class msgHandleActor(gameworld:World) extends Actor {
  import Tcp._
  import context.system
  var buffer = ""
  val delimiter = "~"

  override def receive: Receive = {

    case message: heartbeatType =>
      val js_parsed: JsValue = Json.parse(message.message)
      sender() ! SendToClients(socket_heartbeat(js_parsed,gameworld))


    case message: joingameType =>
      val js_parsed: JsValue = Json.parse(message.message)
      sender() ! SendToClients(socket_joingame(js_parsed,gameworld))


    case message: leavegameType =>
      val js_parsed: JsValue = Json.parse(message.message)
      sender() ! SendToClients(socket_leavegame(js_parsed,gameworld))
  }



  def socket_heartbeat(in_json:JsValue,gameworld:World):String={
    val in_username: String = (in_json \ "username").as[String]
    val in_time: Int = (in_json \ "time").as[Int]
    val in_location: Array[Array[Double]] = (in_json \ "location").as[Array[Array[Double]]]
    val in_action: Map[String, Array[Double]] = (in_json \ "action").as[Map[String, Array[Double]]]

    //no "action" is gong to incloud in Demo2
    if(!(in_action.getOrElse("split",Array(0,0)) sameElements Array(0,0))){
      // some work with split which will not include in Demo2
    }

    if(!(in_action.getOrElse("bobble",Array(0,0)) sameElements Array(0,0))){
      // some work with action "bobble" which will not include in Demo2
    }
    var lcl_Json:Map[String,JsValue] = Map(
      "username" -> Json.toJson(in_username),
      "msgtype" -> Json.toJson("heartbeat"),
      "endtime"->Json.toJson(gameworld.endtime_Timestamp),
      "state" -> Json.toJson(200)
    )
    var player = new Player("empty")


    // s for server     c for client
    for(i <- gameworld.player){
      if(i.name == in_username){
        player = i
      }
    }

    val s_numOfSub = player.sub.length
    //println(s_numOfSub)
    val c_numOfSub = in_location.length
    if(s_numOfSub == 0){
      lcl_Json = lcl_Json + ("state" -> Json.toJson(301))
      //301- Player die
    } else {
      if(s_numOfSub >= c_numOfSub){
        //服务端数量大于等于客户端数量
        //The server has more or equal amount of player_sub then client think it has
        for(i <- 0 until c_numOfSub){
          physics.Physics.locationFixed(player.sub(i),in_location(i),gameworld.Boundary)
        }
        if(s_numOfSub > c_numOfSub){
          lcl_Json = lcl_Json + ("state" -> Json.toJson(501))
          //501- Server has more player_sub then Client, pls check
        }


      } else{
        //服务端数量小于客户端数量
        //The server has less player_sub then client has
        for(i <- 0 until s_numOfSub){
          physics.Physics.locationFixed(player.sub(i),in_location(i),gameworld.Boundary)
        }
        lcl_Json = lcl_Json + ("state" -> Json.toJson(503))
        //503- Server has less player_sub then client, pls check
      }
    }

    lcl_Json = lcl_Json ++ socket_heartbeat_prepareEntityInfoInJson(player.name,gameworld)
    //println(lcl_Json.toString())
    //println(Json.stringify(Json.toJson(lcl_Json)))
    Json.stringify(Json.toJson(lcl_Json))
  }

  def socket_heartbeat_prepareEntityInfoInJson(username:String,gameworld:World):Map[String,JsValue]={
    var playerinfo_user:JsValue = null
    Json.toJson("")
    var Listbuf: List[Array[JsValue]] = List()

    for(p <- gameworld.player){
      if(p.name == username){
        if(p.sub.length >= 0){
          playerinfo_user = Json.toJson(Json.toJson(Array(Json.toJson(p.name)),Json.toJson(p.tojson_pre())))
        } else{
          playerinfo_user = Json.toJson(username,Array(0,0,0,0,0,0))
        }

      } else {
        Listbuf = Array(Json.toJson(p.name), Json.toJson(p.tojson_pre())) +: Listbuf
      }

    }

    var Listbuf2: List[JsValue] = List()
    for(p <- gameworld.bobble){
      Listbuf2 = Json.toJson(p.tojson_pre()) +: Listbuf2
    }

    val jsonMap: Map[String, JsValue] = Map(
      "player" -> playerinfo_user,
      "otherplayer" -> Json.toJson(Listbuf),
      "resourceball" -> gameworld.JS_resourceball,
      "bubble" -> Json.toJson(Listbuf2),
      "thorn" -> gameworld.JS_thorn)
    jsonMap
  }
  def socket_joingame(in_json:JsValue,gameworld:World):String={
    val username: String = (in_json \ "username").as[String]
    if(username == ""){
      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(301),
        "player" -> Json.toJson(""),
        "boundary" -> Json.toJson(gameworld.Boundary),
        "msgtype" -> Json.toJson("joingame"))
      return Json.stringify(Json.toJson(jsonMap))
    }
    if(physics.main.join(username,gameworld)) {
      var playerinfo_user:JsValue = null
      breakable{
        for (p <- gameworld.player) {
          if (p.name == username) {
            playerinfo_user = Json.toJson(Array(Json.toJson(p.name), Json.toJson(p.tojson_pre())))
            break()
          }
        }
      }

      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(200),
        "player" -> Json.toJson(playerinfo_user),
        "boundary" -> Json.toJson(gameworld.Boundary),
        "endtime" -> Json.toJson(gameworld.endtime_Timestamp),
        "msgtype" -> Json.toJson("joingame"))
      Json.stringify(Json.toJson(jsonMap))
    }else {
      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(401),
        "player" -> Json.toJson(""),
        "boundary" -> Json.toJson(gameworld.Boundary),
        "msgtype" -> Json.toJson("joingame"))
      Json.stringify(Json.toJson(jsonMap))
    }
  }
  def socket_leavegame(in_json:JsValue,gameworld:World):String={
    val username: String = (in_json \ "username").as[String]
    if(username == ""){
      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(301),
        "msgtype" -> Json.toJson("leavegame"))
      return Json.stringify(Json.toJson(jsonMap))
    }
    if(physics.main.leave(username,gameworld)){
      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(200),
        "msgtype" -> Json.toJson("leavegame"))
      Json.stringify(Json.toJson(jsonMap))
    }else {
      val jsonMap: Map[String, JsValue] = Map(
        "username" -> Json.toJson(username),
        "state" -> Json.toJson(407), //407 this name is not in game
        "msgtype" -> Json.toJson("leavegame"))
      Json.stringify(Json.toJson(jsonMap))
    }
  }
}

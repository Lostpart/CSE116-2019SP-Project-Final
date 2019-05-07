package tests
import java.net.ServerSocket

import objects._
import physics._
import org.scalatest._
import physics.main.startGame
import play.api.libs.json.{JsValue, Json}

class test3 extends FunSuite{
  test("Join game test"){

    var players:List[Player] = List()
    var subs:List[PlayerSub] = List()
    var lcl_sub:PlayerSub = new PlayerSub("test")
    var player:Player = new Player("test")
    var name:String = ""
    var gameworld:World = new World
    physics.main.startGame(gameworld,Array(500,500),5000,600,0)
    var result:String = new net_socket.basic(null,gameworld).socket_joingame(Json.parse("""{"username":"Brigitte","msgtype":"joingame"}"""),gameworld)
    //{"state":200,"username":"Brig","msgtype":"joingame","boundary":[500,500],"endtime":1554099368,"player":["Brig",[[126,202,12,2,2,51]]]}

    var js_parsed: JsValue = Json.parse(result)
    var msgtype: String = (js_parsed \ "msgtype").as[String]
    var state: Int = (js_parsed \ "state").as[Int]
    var username: String = (js_parsed \ "username").as[String]
    assert(msgtype == "joingame" && state == 200 && username == "Brigitte")

    assert(gameworld.player.exists(player => player.name == "Brigitte")) //Test if Brigitte is in this game



    result = new net_socket.basic(null,gameworld).socket_leavegame(Json.parse("""{"username":"Brigitte","msgtype":"leavegame"}"""),gameworld)
    js_parsed = Json.parse(result)
    msgtype = (js_parsed \ "msgtype").as[String]
    state = (js_parsed \ "state").as[Int]
    username = (js_parsed \ "username").as[String]
    assert(msgtype == "leavegame" && state == 200 && username == "Brigitte")

    assert(!gameworld.player.exists(player => player.name == "Brigitte")) //Test if Brigitte is remove from this game


  }
}

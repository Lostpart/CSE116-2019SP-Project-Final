package main

import io.socket.client.{IO, Socket}

class World_C {
  /** World_c is the short for World_Client
    *
    * it means almost the same as World in the server(Core)
    */

  var username: String = "Shiluo_Test"
  var user: Player_C =new Player_C("noname")
  var bobble:Array[Item_C]=Array()
  var player:Array[Player_C]=Array()
  var thorn:Array[Item_C]=Array()
  var resourceball:Array[Item_C]=Array()
  var endtime_Timestamp:Long = 0
  var Boundary:Array[Int]=Array(1000,1000)
  var loginsuccess:Boolean = false
  var playerdie:Boolean = false

  var token: String = ""
  var sc_ip: String = "127.0.0.1"
  var sc_port: Int = 19199
  var socket:Socket = null

  def update(delta:Long/* delta here is in ms not ns*/):Unit={
//    var speed_angle : Double = Math.atan(user.heading(1)/user.heading(0))
//    if(user.heading(0) < 0){
//      speed_angle += Math.PI
//    }
//    val speed_vector : Array[Double] = Array(Math.cos(speed_angle),Math.sin(speed_angle))

    var speed_vector : Array[Double] = Array(0,0)
    if(user.heading(0)!= 0 && user.heading(1) != 0){
      speed_vector = Array(user.heading(0)*0.7071,user.heading(1)*0.7071)
    } else {
      speed_vector = Array(user.heading(0),user.heading(1))
    }

    for(s <- user.sub){
      s.x = s.x + speed_vector(0)*s.speed*(delta.toDouble/1000d)
      s.y = s.y + speed_vector(1)*s.speed*(delta.toDouble/1000d)


      if(s.x>Boundary(0)){
        s.x = Boundary(0)
      }
      if(s.y>Boundary(1)){
        s.y = Boundary(1)
      }
      if(s.x<0){
        s.x = 0
      }
      if(s.y<0){
        s.y = 0
      }


    }

  }
}

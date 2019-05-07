package main

class Player_C(var name:String) {
  /** Player_c is the short for Player_Client
    *
    * it means almost the same as Player in the server(Core)
    */

  var sub:List[Item_C]=List()
  var color:Int = 0
  var heading:Array[Int] = Array(0,0) // the direction player is heading
  var action_split:Boolean = false
  var action_bobble:Boolean = false

  def moveX(movex:Int): Unit ={
    heading(0) =  movex
  }
  def moveY(movey:Int): Unit ={
    heading(1) =  movey
  }

}

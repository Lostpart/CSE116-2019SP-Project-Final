package tests
import objects._
import physics._
object generater {

  def main(args: Array[String]): Unit = {
    for(i <- 1 until 101){
      generater1(i)
    }
//    generater3(i)
  }

  def generater1(testnumber:Int):Unit ={
      //      item1.location = Array(0,0)
      //      item1.speed = 0
      //      distantmoved = Array(0,0)
      //      Board = Array(0,0)
      //      physics.locationfixed(item1,distantmoved,Board)
      //      assert(loc_same(item1.location,Array(0,0)))
      var item1:Item = new PlayerSub("test1")
      item1.location = Array((new util.Random).nextInt(1000),(new util.Random).nextInt(1000))
      item1.speed = (new util.Random).nextInt(90)
      var distantmoved: Array[Double] = Array(item1.location(0) + (new util.Random).nextInt(100)-50,item1.location(1) +(new util.Random).nextInt(100)-50)
      var Board:Array[Int] = Array((new util.Random).nextInt(1000)+250,(new util.Random).nextInt(1000)+250)


      println("")
      println("    // auto generate Test # "+testnumber.toString)
      println("    item1.location = Array("+item1.location(0).toString+","+item1.location(1).toString+")")
      println("    item1.speed = "+item1.speed.toString)
      println("    distantmoved = Array("+distantmoved(0).toString+","+distantmoved(1).toString+")")
      println("    Board = Array("+Board(0).toString+","+Board(1).toString+")")
      println("    Physics.locationFixed(item1,distantmoved,Board)")
      Physics.locationFixed(item1,distantmoved,Board)
      println("    assert(loc_same(item1.location,Array("+item1.location(0).toString+","+item1.location(1).toString+")))")

  }
//
//  def generater3(testnumber:Int):Unit ={
//    //    name = ""
//    //    player= new player(name)
//    //    subs = List()
//    //
//    //    lcl_sub = new player_sub(name)
//    //    lcl_sub.resource = 0
//    //    subs = lcl_sub :: subs
//    //
//    //    player.sub= subs
//    //    players = player :: players
//    //
//    //    assert(physics.winner(players).toString == Array("","").toString)
//    var players:List[player] = List()
//    var subs:List[player_sub] = List()
//    var lcl_sub:player_sub = new player_sub("test")
//    var player:player = new player("test")
//    var name:String = ""
//
//    println("")
//    println("    // auto generate Test # "+testnumber.toString)
//    println("    players = List()")
//    for(p <- 0 until (new util.Random).nextInt(9)+1) {
//      name = "testplayer" + (new util.Random).nextInt(500)
//      player = new player(name)
//      subs = List()
//      println("    name = \"" + name + "\"")
//      println("    player= new player(name)")
//      println("    subs = List()")
//      for (i <- 0 until (new util.Random).nextInt(4)+1) {
//        lcl_sub = new player_sub(name)
//        lcl_sub.resource = (new util.Random).nextInt(1000)
//        subs = lcl_sub :: subs
//        println("    lcl_sub = new player_sub(name)")
//        println("    lcl_sub.resource = "+lcl_sub.resource.toString)
//        println("    subs = lcl_sub :: subs")
//      }
//      player.sub= subs
//      players = player :: players
//      println("    player.sub= subs")
//      println("    players = player :: players")
//    }
//
//    val result = physics.winner(players)
//    println("    assert(physics.winner(players) sameElements Array(\""+result(0)+"\",\""+result(1)+"\"))")
//
//
//

//  }
}

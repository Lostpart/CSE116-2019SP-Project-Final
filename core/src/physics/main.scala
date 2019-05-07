package physics

import java.net.{ServerSocket, Socket}

import net_socket.socket_server

import scala.io._
import objects._

import scala.util.Random
import scala.util.control.Breaks._
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

object main {

  def join(username:String,gameWorld:World): Boolean ={
    for(player <- gameWorld.player){
      if(username == player.name){
        return false
      }
    }
    var player = new Player(username)
    var player_sub = new PlayerSub(username)

    var lcl_collision:Boolean = true
    do {
      lcl_collision = false
      player_sub.location = Array((new util.Random).nextInt(gameWorld.Boundary(0)),(new util.Random).nextInt(gameWorld.Boundary(1)))
      player_sub.resource = 12

      breakable {
        for(p<- gameWorld.player){
          for(ps <- p.sub){
            if(physics.Physics.collisionDetection(player_sub,ps)){
              lcl_collision = true
              break()
            }
          }
        }
      }

    } while (lcl_collision)



    player_sub.resource = 12
    player_sub.sizecal()
    player_sub.color= (new Random).nextInt(8)

    //player_sub.lastUpdateTime=System.currentTimeMillis()
    player.sub= List(player_sub)
    gameWorld.player = player :: gameWorld.player
    true
  }

  def leave(username:String,gameWorld:World): Boolean ={
    var player_exist:Boolean = false
    var player = new Player(username)
    for(p <- gameWorld.player){
      if(username == p.name){
        player_exist = true
        player = p
      }
    }
    if(player_exist){
      var resourcetotal: Int = 0

      for(s <- player.sub){
        resourcetotal += s.resource
        s.resource = 0
      }

      gameWorld.player = gameWorld.player.filter(_.name != username)
      gameWorld.totalResource = gameWorld.totalResource + resourcetotal

      true

    } else {
      false
    }

  }


  def elimination(items:List[Item]): List[Item] ={
    var result:List[Item] = List()
    for(i <- items){
      if(i.resource > 0){
        result =  result :+ i
      }
    }
    result
  }

  def winner(players:List[Player]):Array[String]={
    var winner:String = "N/A"
    var winner_score:Int = 0
    var lcl_total:Int = 0
    for(player <- players){
      lcl_total = 0
      for(sub <- player.sub){
        lcl_total = lcl_total + sub.resource
      }
      if(lcl_total >= winner_score){
        winner = player.name
        winner_score = lcl_total
      }
    }
    Array(winner,winner_score.toString)
  }

  def startGame(lcl_world:World, worldSize:Array[Int] /* (x,y) */,totalResource:Int ,gameTime:Int /*in sec*/,thornNumber:Int /* 20 */):Unit={
    lcl_world.totalResource = totalResource
    lcl_world.endtime = System.currentTimeMillis() + (gameTime * 1000)
    lcl_world.endtime_Timestamp = physics.timeUpdate.getFuture_time(gameTime)
    lcl_world.Boundary = worldSize
    lcl_world.generateThorns(thornNumber)
    lcl_world.generateResourceBall(totalResource)
  }

  def endGame(lcl_world:World): Unit ={
    lcl_world.bobble = List()
    lcl_world.player = List()
    lcl_world.thorn = Array()
    lcl_world.resourceball = List()
    lcl_world.newResource = 0
  }

  def main(args: Array[String]): Unit = {
    while(true){
      // Game data initialization start 开始进行初始化

      val gameWorld:World = new World()
      startGame(gameWorld,Array(100,100),500,600,0)

      // Game data initialization done


      //socket setup start

      //单开一个线程给socket
      val msgHandlerActorSys = ActorSystem("msgHandler")
      var msgHandlerActorGroup: List[ActorRef] = List()
      for(ia <- 0 until 16){
        msgHandlerActorGroup = msgHandlerActorSys.actorOf(Props(classOf[actor_socket.msgHandleActor], gameWorld)) :: msgHandlerActorGroup
      }
      import system.dispatcher

      //new Thread(new socket_server(19199,gameWorld)).start()
      val system = ActorSystem("SocketServer")
      val actor = system.actorOf(Props(classOf[actor_socket.socket_actor], 19199, gameWorld, msgHandlerActorGroup))

      //socket setup end

      //      var totaltime_1:Long = 0
      //      var totaltime_2:Long = 0
      //      var totaltime_3:Long = 0
      //      var totaltime_4:Long = 0


      while(true){

        while(gameWorld.endtime> System.currentTimeMillis()){
          //        totaltime_1 = 0
          //        totaltime_2 = 0
          //        totaltime_3 = 0
          //        totaltime_4 = 0
          val gl_starttime=System.nanoTime
          //println("执行一次世界更新")
          //an update of the world start

          for(p <- gameWorld.player){
            for(p_s <- p.sub){

              //Check collision with resource ball
              //检查是否与资源球有碰撞

              for(r <- gameWorld.resourceball){
                if(Physics.collisionDetection(p_s,r)){

                  r.eat(p_s)
                }
              }


              //Check collision with thorn(not going to use in Demo 2)
              //检查是否与刺有碰撞（如果有则玩家爆掉 初期不打算实现）
              //            for(r <- gameWorld.thorn){
              //              if(physics.collisionDetection(p_s,r)){
              //                r.eat(p_s)
              //              }
              //            }

              //Check collision with bobble
              //检查是否与玩家吐出的球有碰撞
              for(r <- gameWorld.bobble){
                if(Physics.collisionDetection(p_s,r)){
                  r.eat(p_s)
                }
              }

              //Check collision with other player
              //检查是否与其它玩家球有碰撞
              for(r <- gameWorld.player){
                for(r_s <- r.sub)
                  if(Physics.collisionDetection(p_s,r_s)){
                    r_s.eat(p_s)
                  }
              }

            }
          }
          gameWorld.elimination()
          gameWorld.update_JS_thron()
          gameWorld.update_JS_resourceball()
          gameWorld.resuorcemanagetick -= 1
          if(gameWorld.resuorcemanagetick <= 0){
            gameWorld.resourcemanage()
            gameWorld.resuorcemanagetick = 20
          }

          //val gl_starttime_4 = System.nanoTime
          //totaltime_4 = totaltime_4 + (System.nanoTime-gl_starttime_4 )

          //val gl_starttime_1 = System.nanoTime
          //totaltime_1 = totaltime_1 + (System.nanoTime-gl_starttime_1 )

          //val gl_starttime_2 = System.nanoTime
          //totaltime_2 = totaltime_2 + (System.nanoTime-gl_starttime_2 )

          //val gl_starttime_3 = System.nanoTime
          //totaltime_3 = totaltime_3 + (System.nanoTime-gl_starttime_3 )

          //an update of the world done
          val gl_endtime=System.nanoTime
          //        println("整体耗时："+((gl_endtime-gl_starttime)/1000000).toString
          //          + "ms 区块1耗时："+(totaltime_1/1000000).toString
          //          + "ms 区块2耗时："+(totaltime_2/1000000).toString
          //          + "ms 区块3耗时："+(totaltime_3/1000000).toString
          //          + "ms 区块4耗时："+(totaltime_4/1000000).toString +"ms")
          Thread.sleep(Math.max(50-((gl_endtime-gl_starttime)/1000000).toInt,0))
        }


        startGame(gameWorld,Array(100,100),500,600,0)
      }

    }
  }
}

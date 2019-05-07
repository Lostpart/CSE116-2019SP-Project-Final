package physics

import objects._

object Physics {
  def collisionDetection(item1:Item, item2:Item):Boolean={
    // val minDist:Int=((item1.size + item2.size)*0.9).toInt
    val minDist:Double=item1.size + item2.size
    val l1_loc_x:Double=item1.location(0)
    val l1_loc_y:Double=item1.location(1)
    val l2_loc_x:Double=item2.location(0)
    val l2_loc_y:Double=item2.location(1)
    //println(l1_loc_x.toString +"|"+l1_loc_y.toString+"|"+l2_loc_x.toString+"|"+l2_loc_y.toString+"|"+minDist.toString)

    if(Math.abs(l1_loc_x-l2_loc_x) > minDist || Math.abs(l1_loc_y-l2_loc_y) > minDist){
      return false
    }
    val dist = Math.sqrt(Math.pow(l1_loc_x-l2_loc_x,2)+Math.pow(l1_loc_y-l2_loc_y,2))
    dist < minDist
  }


  def locationFixed(item1:Item, distanceMoved:Array[Double], Board:Array[Int]):Unit={
    val l1_loc_x:Double=item1.location(0)
    val l1_loc_y:Double=item1.location(1)
    var l2_loc_x:Double=distanceMoved(0) // Due to a change in update this never means distance moved, now it means the new loacation
    var l2_loc_y:Double=distanceMoved(1)
    val currentTime:Long=System.currentTimeMillis()
    val time:Double = (currentTime - item1.lastUpdateTime)/1000
    //val time:Double = 1.0
    item1.lastUpdateTime = currentTime
    if(time<0){
      item1.location = Array(l1_loc_x,l1_loc_y)
      return
    }
    val maxDist:Int = (item1.speed * time * 2 ).toInt // If the player is two times faster then it should be, stop him



      if(l2_loc_x>Board(0)){
        l2_loc_x = Board(0)
      }
      if(l2_loc_y>Board(1)){
        l2_loc_y = Board(1)
      }
    if(l2_loc_x<0){
      l2_loc_x = 0
    }
    if(l2_loc_y<0){
      l2_loc_y = 0
    }

//    if(Math.abs(distanceMoved(0)) > maxDist || Math.abs(distanceMoved(1)) > maxDist){
//      val dist_ratio =maxDist / Math.sqrt(Math.pow(distanceMoved(0),2)+Math.pow(distanceMoved(1),2))
//      //println(item1.name + "moved too fast at ("+l1_loc_x.toString+", "+l1_loc_y.toString+") ratio:"+ dist_ratio.toString)
//      l2_loc_x = l1_loc_x+(distanceMoved(0)*dist_ratio).toInt
//      l2_loc_y = l1_loc_y+(distanceMoved(1)*dist_ratio).toInt
//    }

    item1.location = Array(l2_loc_x,l2_loc_y)

  }

  /**
    * The following function is writen by Andrew Kirchgessner he refuse to participate group meeting after he get 100 on
    * Demo2 and this code never get chance to use due to he refuse to write other function for this project.
    */

//  def splitPlayerSub(player: Player): Unit = {
//    //player subs need new location after split, also
//    //max is 4-cancel that
//
//    //var iterator: Int = player.sub.length
//    //var iterator2: Int = 0
//    for(playerSub <- player.sub){
//      if(playerSub.split>0){
//        val numberToSplitTo: Int = playerSub.split
//        if(numberToSplitTo == 2){
//          val firstNewSubPlayer: PlayerSub = new PlayerSub(player.name)
//          val playerSubResource: Int = playerSub.resource
//          firstNewSubPlayer.resource = playerSubResource/2
//          playerSub.resource = playerSubResource/2
//          player.sub = player.sub :+ firstNewSubPlayer
//          for(sub<-player.sub){
//            sub.sizecal()
//          }
//          playerSub.location(1) = playerSub.location(1) + playerSub.size
//          firstNewSubPlayer.location(1) = firstNewSubPlayer.location(1) - firstNewSubPlayer.size
//        }else if(numberToSplitTo == 4){
//          val firstNewSubPlayer: PlayerSub = new PlayerSub(player.name)
//          val secondNewSubPlayer: PlayerSub = new PlayerSub(player.name)
//          val thirdNewSubPlayer: PlayerSub = new PlayerSub(player.name)
//          val playerSub1Resource: Int = playerSub.resource
//          firstNewSubPlayer.resource = playerSub1Resource/4
//          secondNewSubPlayer.resource = playerSub1Resource/4
//          thirdNewSubPlayer.resource = playerSub1Resource/4
//          playerSub.resource = playerSub1Resource/4
//          player.sub = player.sub :+ firstNewSubPlayer
//          player.sub = player.sub :+ secondNewSubPlayer
//          player.sub = player.sub :+ thirdNewSubPlayer
//          for(playerSub<-player.sub){
//            playerSub.sizecal()
//          }
//          playerSub.location(1) = playerSub.location(1) + playerSub.size
//          playerSub.location(0) = playerSub.location(0) + playerSub.size
//          firstNewSubPlayer.location(1) = firstNewSubPlayer.location(1) - firstNewSubPlayer.size
//          firstNewSubPlayer.location(0) = firstNewSubPlayer.location(0) - firstNewSubPlayer.size
//          secondNewSubPlayer.location(1) = secondNewSubPlayer.location(1) + secondNewSubPlayer.size
//          secondNewSubPlayer.location(0) = secondNewSubPlayer.location(0) - secondNewSubPlayer.size
//          thirdNewSubPlayer.location(1) = thirdNewSubPlayer.location(1) - thirdNewSubPlayer.size
//          thirdNewSubPlayer.location(0) = thirdNewSubPlayer.location(0) + thirdNewSubPlayer.size
//        }
//        playerSub.split = 0
//      }
//    }
//    /*
//    while(iterator<=numberToSplitTo){
//      val NewSubPlayer: PlayerSub = new PlayerSub(player.name)
//      val playerSubResource: Int = player.sub(iterator2).resource
//      NewSubPlayer.resource = playerSubResource/2
//      player.sub(iterator2).resource = playerSubResource/2
//      player.sub = player.sub :+ NewSubPlayer
//      iterator += 1
//      iterator2 += 1
//    }*/
//    for(playerSub<-player.sub){
//      playerSub.sizecal()
//    }
///*  if(player.sub.length == 1){
//      val resourceTotal = player.sub.head.resource
//      player.sub.head.resource = resourceTotal/2
//      player.sub = player.sub :+ new PlayerSub(player.name)
//      player.sub(1).resource = resourceTotal/2
//    }
//
//    for(subPlayer <- player.sub){
//      subPlayer.sizecal()
//    }*/
//    /*
//    val maxSubs: Int = 8
//    //var totalPlayerResource: Int = 0
//    var largestSubIndex: Int = 0
//    var largestSubResource: Int = 0
//    for(subPlayer <- player.sub) {
//      //totalPlayerResource += subPlayer.resource
//      if(subPlayer.resource > largestSubResource){
//        largestSubIndex = player.sub.indexOf(subPlayer)
//        largestSubResource = subPlayer.resource
//      }else{}
//    }
//    if(player.sub.length < maxSubs){
//      player.sub
//    }*/
//  }
}

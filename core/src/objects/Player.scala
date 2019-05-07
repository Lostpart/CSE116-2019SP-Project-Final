package objects

import scala.collection.mutable.ArrayBuffer

class Player(val name: String) {
  var sub:List[PlayerSub]=List()
  var color:Int = 0


  def tojson_pre():Array[Array[Double]]={
    var Arrbuf: ArrayBuffer[Array[Double]] = ArrayBuffer[Array[Double]]()
//    var i:Int = 0
    for(item <- sub){
      //println(item.location(0).toString+"|"+item.location(1).toString+"|"+item.resource.toString+"|"+item.size.toString+"|"+item.color.toString+"|"+item.speed.toString)
      Arrbuf += Array(item.location(0),item.location(1),item.resource,item.size,item.color,item.speed)
//      i += 1
      // [x, y, resource, size, color, speed]
    }

    Arrbuf.toArray

//    if(i != 0){
//      Arrbuf.toArray
//    } else {
//      Array(Array(0,0,0,0,0,0))
//    }

  }

}

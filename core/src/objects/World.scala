package objects

import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}


class World {
  var bobble:List[Bubble]=List()
  var player:List[Player]=List()
  var thorn:Array[Thorn]=Array()
  var resourceball:List[ResourceBall]=List()
  var totalResource: Int = 50000
  var newResource: Int = 0
  var resuorcemanagetick:Int = 20
  var endtime:Long = 0 //the time that this game will end, in ms 【internal use only】 System.currentTimeMillis()
  var endtime_Timestamp:Long = 0 //the time that this game will end in sec 【for out side function】
  var Boundary:Array[Int]=Array(1000,1000)


  //following variable are cache of thorn & resourceball's information which update after every world update.
  var JS_thorn:JsValue = Json.toJson("")
  var JS_resourceball:JsValue = Json.toJson("")

  def update_JS_thron():Unit={
    var Arrbuf: ArrayBuffer[Array[Double]] = ArrayBuffer[Array[Double]]()
    for(t <- this.thorn){
      Arrbuf += t.tojson_pre()
    }
    this.JS_thorn = Json.toJson(Arrbuf.toArray)
  }
  def update_JS_resourceball():Unit={
    var Arrbuf: ArrayBuffer[Array[Double]] = ArrayBuffer[Array[Double]]()
    for(t <- this.resourceball){
      Arrbuf += t.tojson_pre()
    }
    this.JS_resourceball = Json.toJson(Arrbuf.toArray)
  }

  def generateResourceBall(amount: Int): Unit = {
    var lcl_list:ListBuffer[ResourceBall] = new ListBuffer[ResourceBall]
    for(i <- 0 until amount){
      lcl_list +=  new ResourceBall(Array((new util.Random).nextInt(Boundary(0)),
        (new util.Random).nextInt(Boundary(1))),(new util.Random).nextInt(8))

    }
    this.resourceball = lcl_list.prependToList(this.resourceball)
  }

  def generateThorns(amount: Int): Unit = {
    var lcl_thorn:List[Thorn] = List()
    for(i <- 0 until amount){
      lcl_thorn =  new Thorn(Array((new util.Random).nextInt(Boundary(0)),
        (new util.Random).nextInt(Boundary(1))),(new util.Random).nextInt(96)+3) :: lcl_thorn
      lcl_thorn(i).sizecal()
    }
    thorn = lcl_thorn.toArray
  }


  def elimination(): Unit ={

//    var result1:ListBuffer[Bubble] = new ListBuffer[Bubble]
//    for(i <- this.bobble){
//      if(i.resource > 0){
//        result1 += i
//      }
//    }
    this.bobble = this.bobble.filter((i: Bubble) => i.resource>0)

//    var result2:ListBuffer[ResourceBall] = new ListBuffer[ResourceBall]
//    for(i <- this.resourceball){
//      if(i.resource > 0){
//        result2 += i
//      }
//    }
//    this.resourceball = result2.toList
    this.resourceball = this.resourceball.filter((i: ResourceBall) => i.resource>0)


  for(p<- this.player){
    var result3:ListBuffer[PlayerSub] = new ListBuffer[PlayerSub]
    for(i <- p.sub){
      if(i.resource >= 1){
        result3 += i
      }
    }
    //println(p.name +":"+ result3.toList.length.toString)
    p.sub = result3.toList
  }
  }

  def resourcemanage():Unit ={
    for(p<- this.player){
      var result3:ListBuffer[PlayerSub] = new ListBuffer[PlayerSub]
      for(p_s <- p.sub){
        var reduce:Int = (p_s.resource *0.01).toInt
        p_s.resource -= reduce
        newResource += reduce
        p_s.sizecal()
      }
    }

    var reduce:Int = (newResource *0.1).toInt
    if(reduce < 1){
      if(newResource < 1){
        reduce = newResource
      } else {
        reduce = 1
      }
    }
    newResource -= reduce
    generateResourceBall(reduce)

  }
}


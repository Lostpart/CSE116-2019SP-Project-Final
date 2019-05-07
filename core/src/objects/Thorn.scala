package objects

import scala.util.Random

class Thorn(val l_location: Array[Double], val l_resource:Int) extends Item{
  this.location= this.l_location
  this.resource = this.l_resource
  namet= "thorn"


  override def eat(item2:Item):Unit={
    if(this.size < item2.size){
      item2.split = (new Random).nextInt(7)+2 // min 2 max 8
    }
  }

  def tojson_pre():Array[Double]={
    Array(this.location(0),this.location(1),this.size)
    // [x, y, size]  color is always green
  }
}

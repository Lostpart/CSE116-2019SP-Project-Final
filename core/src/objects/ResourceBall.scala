package objects

import play.api.libs.json.{JsArray, JsValue, Json}

import scala.util._
class ResourceBall(var l_location: Array[Double], var l_color:Int) extends Item {

  namet= "resourceball"

  location = this.l_location
  color = this.l_color
  size = 0.5

  override def sizecal(): Unit = {
    if (this.resource == 0) {
      this.size = 0
    }
    else{
      this.size = 0.5
    }
  }

  override def eat(item2: Item): Unit = {
    item2.resource = this.resource + item2.resource
    this.resource = 0
    this.sizecal()
  }

  def tojson_pre():Array[Double]={
    Array(this.location(0),this.location(1),this.color)
    // [x, y, color]
  }
}

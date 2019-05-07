package objects

abstract class Item {
  var name : String = ""
  var namet: String = "" //name of this type
  var size : Double = 1 //radius | dont change it , use sizecal() to change it
  var location: Array[Double] = Array(0,0)
  var color: Int = 1 // a number between 0~7 that representing a color
  var speed: Double = 1 //maxspeed the item can do | dont change it , use sizecal() to change it
  var resource: Int = 1 //how many resource this item have (default 1 for resourceball)
  var split: Int = 0 //how many piece will this item is going to split, change it to zero after "split"
  var velocity: Array[Int] = Array(0,0)
  var lastUpdateTime: Long = 0

  def eat (item2:Item):Unit={} //The entity being eaten runs this method

  def die():Boolean={
    this.size <= 0
  }


  def sizecal():Unit = {
    this.size = Math.sqrt(this.resource / 3)
    this.speed = Math.min((100/this.size)+1,55)
  }

}

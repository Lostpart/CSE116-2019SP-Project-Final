package objects

class Bubble extends Item {
  namet= "bobble"

  override def sizecal(): Unit = {
    if (this.resource == 0) {
      this.size = 0
    }
  }
  override def eat(item2: Item): Unit = {
    item2.resource = this.resource + item2.resource
    this.resource = 0
    item2.sizecal()
    this.sizecal()
  }
  def tojson_pre():Array[Double]={
    Array(this.location(0),this.location(1),this.size,this.color)
    // [x, y, size, color]
  }
}

package objects

class PlayerSub(var username: String) extends Item{
  name= username
  namet= "player_sub"
  resource = 12



  override def eat(item2:Item):Unit={
    if(this.resource > item2.resource){
      this.resource = this.resource + item2.resource
      item2.resource = 0
      item2.sizecal()
      this.sizecal()

    }
    else if(this.resource < item2.resource) {
      item2.resource = this.resource + item2.resource
      this.resource = 0
      item2.sizecal()
      this.sizecal()
    }
  }

  override def die(): Boolean = {
    size >= 1
  }

}

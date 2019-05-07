package main

class Item_C(var x:Double,var y:Double, var size:Double,val color:Double) {
  /** Item_C is the short for Item_Client
    *
    * it means almost the same as World in the server(Core)
    */
  var name : String = ""
  var namet: String = "" //name of this type
  var speed: Double = 1 //maxspeed the item can do | dont change it , use sizecal() to change it
  var resource: Int = 1 //how many resource this item have (default 1 for resourceball)

  def eat (item2:Item_C):Unit={}


}

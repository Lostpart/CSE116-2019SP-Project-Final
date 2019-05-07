package tests
import objects._
import physics._
import org.scalatest._

class test2 extends FunSuite{
  def list_same(list1:List[Item], list2:List[Item]):Boolean={
    var listA:List[Int] = List()
    var listB:List[Int] = List()
    for(i <- list1){
      listA = i.resource :: listA
    }
    for(i <- list2){
      listB = i.resource :: listB
    }
    listA.sorted sameElements listB.sorted

  }
//  test("elimiation test"){
//    var item1:Item = new ResourceBall(Array(50,50),4)
//    item1.resource = 100
//    var item2:Item = new ResourceBall(Array(70,50),4)
//    item2.resource = 50
//    var item3:Item = new ResourceBall(Array(50,30),4)
//    item3.resource = 1
//    var item4:Item = new ResourceBall(Array(10,50),4)
//    item4.resource = 0
//
//    assert(list_same(Physics.elimiation(List(item1,item2)),List(item1,item2)))
//    assert(list_same(Physics.elimiation(List(item1,item4)),List(item1)))
//    assert(list_same(Physics.elimiation(List(item4,item3,item2)),List(item2,item3)))
//    assert(list_same(Physics.elimiation(List(item4,item2)),List(item2)))
//    assert(list_same(Physics.elimiation(List(item4)),List()))
//    assert(list_same(Physics.elimiation(List()),List()))
//    assert(!list_same(Physics.elimiation(List(item4,item2)),List(item3)))
//    assert(!list_same(Physics.elimiation(List(item4,item3,item2)),List(item1,item2,item3)))


}

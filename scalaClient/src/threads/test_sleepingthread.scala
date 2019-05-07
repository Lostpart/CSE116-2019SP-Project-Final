package threads

import gui.{ingame_gui, shapesGroup}
import gui.ingame_gui.testnum
import scalafx.scene.shape.Rectangle

class test_sleepingthread(var testnum:shapesGroup) extends Runnable{
  override def run(): Unit = {
    testnum.shapes = testnum.shapes :+ new Rectangle()
  }
}

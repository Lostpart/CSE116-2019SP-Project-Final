package gui

import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.{Group, Scene}
import threads.test_sleepingthread

object ingame_gui extends JFXApp {

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  val playerCircleRadius:Double = 20
  val playerSpeed: Double = 10
  val rectangleWidth: Double = 60
  val rectangleHeight: Double = 40

  var gl_gui_resourceball: shapesGroup = new shapesGroup
  var gl_gui_user: shapesGroup = new shapesGroup
  var gl_gui_player: shapesGroup = new shapesGroup
  var gl_gui_thorn: shapesGroup = new shapesGroup
  var gl_gui_bubble: shapesGroup = new shapesGroup

  var gl_topLeft_coordinate: Array[Double]= Array(0,0) // ↑ the coordinate in the world where the top left of the window is a





  var allRectangles: List[Shape] = List()
  var sceneGraphics: Group = new Group {}


  val player: Circle = new Circle {
    centerX = Math.random() * windowWidth
    centerY = Math.random() * windowHeight
    radius = playerCircleRadius
    fill = Color.Green
  }
  sceneGraphics.children.add(player)


  def drawRectangle(centerX: Double, centerY: Double): Unit = {
    val newRectangle = new Rectangle() {
      width = rectangleWidth
      height = rectangleHeight
      translateX = centerX - rectangleWidth / 2.0
      translateY = centerY - rectangleHeight / 2.0
      fill = Color.Blue
    }
    sceneGraphics.children.add(newRectangle)
    allRectangles = newRectangle :: allRectangles
  }


  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => player.translateY.value -= playerSpeed
      case "A" => player.translateX.value -= playerSpeed
      case "S" => player.translateY.value += playerSpeed
      case "D" => player.translateX.value += playerSpeed
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }

  var testnum:shapesGroup = new shapesGroup
  testnum.shapes = testnum.shapes :+ new Rectangle()

  this.stage = new PrimaryStage {
    this.title = "2D Graphics"
    scene = new Scene(windowWidth, windowHeight) {
      content = List(sceneGraphics)

      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))

      // add an EventHandler[MouseEvent] to draw a rectangle when the player clicks the screen
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => drawRectangle(event.getX, event.getY))
    }

    // define a function for the action timer (Could also use a method)
    // Rotate all rectangles (relies on frame rate. lag will slow rotation)
    val update: Long => Unit = (time: Long) => {
      for (shape <- allRectangles) {
        shape.rotate.value += 0.5

//        new Thread(new test_sleepingthread(testnum)).start()
//        println(testnum.shapes.length)
      }
    }

    // Start Animations. Calls update 60 times per second (takes update as an argument)
    AnimationTimer(update).start()
  }



    //
    println("更新")
    Thread.sleep(3000)

}

package main

import control.ingame
import gui.shapesGroup
import gui.button
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.text._
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.control.{Button, Label, ProgressBar, TextField}
import threads.WorldUpdate

import scala.util.control.Breaks.breakable
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import network.HandleMessagesFromPython
import scalafx.scene.layout.GridPane
import org.apache.commons.lang

object main extends JFXApp{

  /**
    * 定义区
    */
  def color_decode(number:Double):Color={
    number.toInt match{
      case 0 => Color.Indigo
      case 1 => Color.Red
      case 2 => Color.Orange
      case 3 => Color.Yellow
      case 4 => Color.Green
      case 5 => Color.Blue
      case 6 => Color.Purple
      case 7 => Color.Pink
      case 8 => Color.DarkGreen
      case _ => Color.Indigo
    }
  }

  def color_decode_text(number:Double):Color={
    number.toInt match{
      case 0 => Color.Black
      case 1 => Color.Black
      case 2 => Color.Black
      case 3 => Color.Black
      case 4 => Color.Black
      case 5 => Color.White
      case 6 => Color.White
      case 7 => Color.Black
      case 8 => Color.White
      case _ => Color.White
    }
  }


  def CalCenter(player:Player_C,/*windowWidth*/WW: Double, /*windowHeight*/WH: Double): Array[Array[Double]] ={
    var x_sum: Double = 0
    var y_sum: Double = 0
    var total: Double = 0

    var x_max: Double = player.sub.head.x
    var x_min: Double = player.sub.head.x
    var y_max: Double = player.sub.head.y
    var y_min: Double = player.sub.head.y
    for(s <- player.sub){
      x_sum += s.x
      y_sum += s.y
      total += 1

      if(s.x + s.size > x_max){
        x_max = s.x + s.size
      }
      if(s.x - s.size < x_min){
        x_min = s.x - s.size
      }
      if(s.y + s.size > y_max){
        y_max = s.y + s.size
      }
      if(s.y - s.size < y_min){
        y_min = s.y - s.size
      }

    }
    Array(Array((x_max-x_min)*8/WW,(y_max-y_min)*8/WH),Array(x_sum/total,y_sum/total))
  }
  def drawCircle(x: Double, y: Double, size:Double, color:Double, topLeft:Array[Double], Scaling:Double): Unit = {
    val newCircle = new Circle() {
      centerX = (x - topLeft(0)) / Scaling
      centerY = (y - topLeft(1)) / Scaling
      radius = size / Scaling
      fill = color_decode(color)
    }
    sceneGraphics.children.add(newCircle)
  }

  def drawCircle_withname(x: Double, y: Double, size:Double, color:Double,name:String, topLeft:Array[Double], Scaling:Double): Unit = {
    val newCircle = new Circle() {
      centerX = (x - topLeft(0))/Scaling
      centerY = (y - topLeft(1))/Scaling
      radius = size/Scaling
      fill = color_decode(color)
    }
    val text = new Text(name)

    text.setFont(new Font(findtextFontsize(size/Scaling,name)))
    text.fill = color_decode_text(color)
    text.x =((x - topLeft(0))/Scaling)-(text.getLayoutBounds.getWidth/2)
    text.y =((y - topLeft(1))/Scaling)+(text.getLayoutBounds.getHeight/4)

    //text.setBoundsType(TextBoundsType.Visual)

    sceneGraphics.children.addAll(newCircle,text)
  }

  def findtextFontsize(r:Double,text: String):Int={
    Math.floor(4 * r / text.length).toInt
  }

  /**
    * 启动初始化
    */

  var gameworld = new World_C

  //启动socket客户端
  gameworld.sc_ip = "127.0.0.1"
  gameworld.sc_port = 9199

  //直接发送加入游戏的信息
  //  network.network_socket.joingame(gameworld,"Shiluo")
  //
  //  network.network_socket.heartbeat(gameworld,true)
  import io.socket.client.IO

  val opts = new IO.Options()
  opts.forceNew = true
  opts.reconnection = true

  val socket: Socket = IO.socket("http://" + gameworld.sc_ip + ":" + gameworld.sc_port.toString + "/")
  socket.on("heartbeat", new HandleMessagesFromPython(gameworld,this))
  socket.on("joingame", new HandleMessagesFromPython(gameworld,this))
  socket.on("leavegame", new HandleMessagesFromPython(gameworld,this))
  //socket.on("testmsg", new HandleMessagesFromPython(gameworld))
  socket.connect()

//  socket.emit("testmsg","test1")
//  socket.emit("testmsg","test2")
//  socket.emit("testmsg","test3")
  gameworld.socket = socket

  gameworld.username = lang.RandomStringUtils.randomAlphanumeric(8)

  network.webSocketSender.joingameSend(gameworld)

  while(!gameworld.loginsuccess){
    Thread.sleep(50)
  }

  //启动世界更新线程
  new Thread(new WorldUpdate(gameworld,socket,this)).start()







  //窗口设置
  val windowWidth: Double = 800
  val windowHeight: Double = 600
  val usernametextfield:TextField =new TextField {maxWidth = 40}
  //var gl_topLeft_coordinate: Array[Double]= Array(0,0) // the coordinate where the top left of the window is at
  //var gl_Scaling:Double = 1.0 // 1 pixel on screen = gl_Scaling unit in world_C
  var gl_lastGUIupdate:Long = System.nanoTime()

  //var allCircle: List[Shape] = List()
  var sceneGraphics: Group = new Group {}
  var nextupdate:Boolean = false

  //   gameStage is now in network.HandleMessageFromPython.joingameRecive
  this.stage = new PrimaryStage {
    this.title = "CSE116 Personal Project Demo 3"

    this.scene = new Scene(windowWidth, windowHeight) {
      content = List(sceneGraphics)

      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.ANY, new ingame(gameworld.user))

      // add an EventHandler[MouseEvent] to draw a rectangle when the player clicks the screen
      //addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => drawRectangle(event.getX, event.getY))
    }

    // define a function for the action timer (Could also use a method)
    // Rotate all rectangles (relies on frame rate. lag will slow rotation)
    val update: Long => Unit = (time: Long) => {
      val delta:Long = (time - gl_lastGUIupdate)/1000000
      gameworld.update(delta)
      gl_lastGUIupdate = time

      if(gameworld.playerdie){
        this.close()
      }


      sceneGraphics.children.clear()
      var lcl_data:Array[Array[Double]] = CalCenter(gameworld.user,windowWidth,windowHeight)
      var lcl_Scaling: Double = math.max(0.05,math.max(lcl_data(0)(0),lcl_data(0)(1)))
      var lcl_topLeft_coordinate: Array[Double] = Array(lcl_data(1)(0)-(windowWidth/2)*lcl_Scaling,lcl_data(1)(1)-(windowHeight/2)*lcl_Scaling)
      for (r <- gameworld.resourceball) {
        drawCircle(r.x,r.y,r.size,r.color,lcl_topLeft_coordinate,lcl_Scaling)
      }
      for (r <- gameworld.bobble) {
        drawCircle(r.x,r.y,r.size,r.color,lcl_topLeft_coordinate,lcl_Scaling)
      }
      for(p <- gameworld.player){
        for (r <- p.sub) {
          drawCircle_withname(r.x,r.y,r.size,r.color,p.name,lcl_topLeft_coordinate,lcl_Scaling)
        }
      }
      for (r <- gameworld.user.sub) {
        drawCircle_withname(r.x,r.y,r.size,r.color,r.name,lcl_topLeft_coordinate,lcl_Scaling)
      }

    }

    // Start Animations. Calls update 60 times per second (takes update as an argument)
    AnimationTimer(update).start()
  }

  //  this.stage = new PrimaryStage {
  //    this.title = "CSE116 Personal Project Demo 3"
  //
  //
  //    this.scene = new Scene(windowWidth, windowHeight) {
  //
  //      content = List(
  //        new GridPane {
  //          add(usernametextfield,1,0)
  //          add(new gui.startgame(gameworld,usernametextfield, 1, 2), 1, 2)
  //        }
  //      )
  //    }
  //    }



  //allCircle = newCircle :: allCircle


}


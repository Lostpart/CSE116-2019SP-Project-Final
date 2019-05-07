package threads

import main.World_C

import scala.collection.mutable.ArrayBuffer
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import scalafx.application.JFXApp

class WorldUpdate(gameworld:World_C,socket:Socket, GUIwindow: JFXApp) extends Runnable{
  override def run(): Unit = {

    while(true){
      val gl_starttime=System.nanoTime

      if(main.timeUpdate.getCurrent_time() < gameworld.endtime_Timestamp){
        network.webSocketSender.heartbeatSend(gameworld,socket)
      }

      val gl_endtime=System.nanoTime
      //println((gl_endtime-gl_starttime)/1000000d)
      Thread.sleep(Math.max(50-((gl_endtime-gl_starttime)/1000000).toInt,0))
    }
  }
}

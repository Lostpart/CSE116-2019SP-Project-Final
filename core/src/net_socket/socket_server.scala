package net_socket

import java.io.{IOException, InputStream, OutputStream}
import java.net.{ServerSocket, Socket}

import objects.World

class socket_server(val port:Int,val gameworld:World)  extends Runnable{

  override def run(): Unit = {
    println("Starting Socket Server")
    try{
      var ss:ServerSocket = new ServerSocket(port)

      while(true){
        val sc:Socket = ss.accept()
        new Thread(new basic(sc,gameworld)).start()
      }


    } catch{

      case ex: IOException => {
        println("IO Exception on:" + getClass)
      }
//      case _ => {
//        println("Unknown error on:" + getClass)
//      }
    }

  }
}

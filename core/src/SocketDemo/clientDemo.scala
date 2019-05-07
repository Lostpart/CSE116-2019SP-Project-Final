package SocketDemo
import java.net.{ServerSocket,Socket}
import java.io._
object clientDemo {
  def main(args: Array[String]): Unit = {
    //向服务端请求
    //this is the Client
    //sending data to Server
    var sc:Socket = new Socket("127.0.0.1",19199)
    var input:InputStream = sc.getInputStream
    var output:OutputStream = sc.getOutputStream

    /**
      * 发送数据 Send data
      */

    //利用output流 Use output flow (work similar as writing file)
    output.write("abcdefg".getBytes())





    /**
      * 接收数据 receive data
      */
    // get first 1024 Byte data (which usually is more then enough, but you can always change the size)
    // work similar as reading file
    var result:Array[Byte]=new Array[Byte](1024)
    var result_len:Int = input.read(result)
    var result_str:String = new String(result,0,result_len)
    println(result_str)

    //remember to close connect
    input.close()
    output.close()
    sc.close()
  }
}

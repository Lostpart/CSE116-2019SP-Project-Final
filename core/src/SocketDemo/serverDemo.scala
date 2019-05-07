package SocketDemo
import java.net.{ServerSocket,Socket}
import java.io._
object serverDemo {
  def main(args: Array[String]): Unit = {
    //启动服务端
    //Starting server
    var ss:ServerSocket = new ServerSocket(19199)
    //监听端口 等待并接收客户端请求，建立socket连接（阻塞方法）
    // listen to the port, waiting for connection from client ( it will keep wait until a connect come)

    var sc:Socket = ss.accept()
    var input:InputStream = sc.getInputStream
    var output:OutputStream= sc.getOutputStream

    /**
      * 收数据 receive data
      */
    //从连接中接收数据,需要获取一个输入流工具
    // get first 1024 Byte data (which usually is more then enough, but you can always change the size)
    // work similar as reading file

    var result:Array[Byte]=new Array[Byte](1024)
    var result_len:Int = input.read(result)
    var result_str:String = new String(result,0,result_len)
    println(result_str)


    /**
      * 发数据 Send data
      */


    output.write("test".getBytes())

    output.close()
    input.close()
    sc.close()



  }
}

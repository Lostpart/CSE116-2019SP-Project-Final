package main

import java.text.SimpleDateFormat
import java.util.Date


object timeUpdate {
  val now = new Date()
  def getCurrent_time(): Long = {
    val a = now.getTime
    var str = a+""
    str.substring(0,10).toLong
  }
  def getFuture_time(futuretime:Int/*in sec*/):Long={
    val a = now.getTime
    var str = a+""
    str.substring(0,10).toLong + futuretime
  }

}

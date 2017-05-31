/**
  * Created by tca on 9/21/16.
  */
package com.trace3.spark.examples


import org.apache.spark.sql.SparkSession
import com.trace3.spark.util.ParseOpts


object OptionTest {

  def usage =
    """
      |OptionTest --input <file> --table <name> arg1 arg2 arg3
      |
    """.stripMargin


  def main ( args: Array[String] ) : Unit = {
    val spark = SparkSession.builder()
      .appName("OptionTest")
      .getOrCreate()

    val (optMap, optList) = ParseOpts.parseOpts(args.toList)

    val fileName  = optMap.getOrElse("input", None)
    val tableName = optMap.getOrElse("table", None)

    //if ( fileName.getClass() == String )
      println(" ===>  fileName = " + fileName)
    //else
      //println("fileName is None")
    println(" ===>  table = " + tableName)

    if ( optList.length < 3 )
      println(" ===>  missing args")

    optList.foreach { x => println(" ===>  " + x + " " ) }
  }
}

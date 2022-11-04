/**  ParseOpts.scala - A simple scala command-line parser
  *
  *  @author Timothy C. Arland <tcarland@gmail.com>
  *
 **/
package com.trace3.util

import scala.collection.immutable.{List, Map}


/**  An argument/option parser where command line flags with no argument
 *   are defined as '-x' switches (boolean) and long options are defined as
 *   '--long-opt <val>' with a required argument. Any remaining arguments
 *   not defined as a switch are returned as a List.
 **/
object ParseOpts {

  type OptMap  = Map[String, String]
  type OptList = List[String]

  val Version  = """v1.5.0"""


  def parseOpts ( args: OptList ) : (OptMap, OptList)  =
  {
    def nextOpt ( argList: OptList, optMap: OptMap ) : (OptMap, OptList) = {
      val longOpt = """^--(\S+)""".r
      val regOpt  = """^-(\S+)""".r

      argList match {
        case Nil  => (optMap, argList)
        case longOpt(opt) :: value :: tail => nextOpt(tail, optMap ++ Map(opt -> value))
        case regOpt(opt)           :: tail => nextOpt(tail, optMap ++ Map(opt -> null))
        case _    => (optMap, argList)
      }
    }

    nextOpt(args, Map())
  }

}

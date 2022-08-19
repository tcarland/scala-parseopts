import com.trace3.util.ParseOpts

object ParseOptsTest {

  val usage : String =
    """
      |ParseOptsTest <args>
      |
    """.stripMargin


  def main ( args: Array[String] ) : Unit = {
    val (optMap, optList) = ParseOpts.parseOpts(args.toList)

    if ( args.size < 2 )
        println(usage)

    println("Args: " + args.size.toString)
    args.foreach { x => println(" => '" + x + "'")}
    println("")

    println(" ==> OptionMap: " + optMap.size.toString)
    optMap.foreach { x => println(" ===>    " + x._1 + " -> " + x._2) }

    println(" --> OptionList: " + optList.size.toString)
    optList.foreach { x => println(" -->    " + x) }

    if ( (optList.size + optMap.size) == (args.size - 1) )
        println("SUCCESS")
    else
        println("ERROR")
        sys.exit(1)
  }

}

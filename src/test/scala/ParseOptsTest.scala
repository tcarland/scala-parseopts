import com.trace3.util.ParseOpts

object ParseOptsTest {

  val usage : String =
    """
      |ParseOptsTest <args>
      |
    """.stripMargin


  def main ( args: Array[String] ) : Unit = {
    val (optMap, optList) = ParseOpts.parseOpts(args.toList)

    println("Args: " + args.length.toString)
    args.foreach { x => println(" => '" + x + "'")}
    println("")

    println(" ==> OptionMap:" + optMap.size.toString)
    optMap.foreach { x => println(" ===>    " + x._1 + " -> " + x._2) }

    println(" --> OptionList: " + optList.length.toString)
    optList.foreach { x => println(" -->    " + x) }
  }

}

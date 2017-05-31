Spark CommandLine Options
==========================

A simple approach to parsing command-line options provided to a spark job. This supports both long options (--longopt) and regular options (-o), but does not work with combined short opts like (-adcf). We actually impose a restriction on parameter values to be provided only as a long option. 

It will additionally support non-options as long as they are provided last. (--opt1 foo -f -d argX argY argZ). As a result, the options are returned as both a (***scala.collection.immutable***) **Map** of options and a **List** of arguments.

This provides a bit more robustness in providing arguments to a spark job since the order of options is now interchangeable and generally cleaner than just providing flat arguments to spark-submit. 

We can also simply pass-through the command-line options when creating a spark-submit wrapperscript:

```
#!/bin/bash
MYCLASS="com.foo.bar.example"
MYJAR="myexample-0.1.jar"

spark-submit --master yarn --class $MYCLASS $MYJAR $@
```

The code for performing this is relatively straight-forward:

```
object ParseOpts {
  type OptMap  = Map[String, String]
  type OptList = List[String]
  
  def parseOpts ( args: OptList ) : (OptMap, OptList)  = 
  {
    def nextOpt ( argList: OptList, optMap: OptMap ) : (OptMap, OptList) = {
      val longOpt = "^--(\\S+)".r
      val regOpt  = "^-(\\S+)".r

      argList match {
        case Nil => (optMap, argList)
        case longOpt(opt)  :: value  :: tail => nextOpt(tail, optMap ++ Map(opt -> value))
        case regOpt(opt)             :: tail => nextOpt(tail, optMap ++ Map(opt -> null))
        case _ => (optMap, argList)
      }
    }
    
    nextOpt(args, Map())
  }

}
```


-tca




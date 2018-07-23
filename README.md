Scala Command Line Options Parser
=================================

A simplified scala approach to parsing command-line options. This supports 
both long options (--longopt) and short/regular options (-o). Short options 
do not work combined (-asdf), but must be provided individually (-a -s -d -f). 
There is an additional restriction that short options are boolean switches 
only and do not take arguments. Long options, on the other hand, must have 
arguments. 

Conveniently ParseOpts will additionally support non-options as long as they 
are provided last:
```
myApp -f -d --foo1 bar argX argY argZ  
```
To support this feature, options are returned as a 
*scala.collection.immutable*.**Map** of options and **List** of 
remaining arguments.

This simplified approach works well with distributed uses of scala (ie. spark). 
And at the least provides the flexibility of allowing options to be clearly 
defined as well as being provided in any order. Lastly, it offers a some clarity 
when using launch scripts to wrap a scala application. For example:

```
#!/bin/bash
MYCLASS="com.foo.bar.example"
MYJAR="myexample-0.1.jar"

spark-submit --master yarn \
  $MYOTHERSPARKOPTS \
  --class $MYCLASS \
  $MYJAR \
  $@ 
```

The code for performing the parsing is relatively straight-forward and consists 
primarily of the following scala function:

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
        case longOpt(opt) :: value :: tail => nextOpt(tail, optMap ++ Map(opt -> value))
        case regOpt(opt)           :: tail => nextOpt(tail, optMap ++ Map(opt -> null))
        case _ => (optMap, argList)
      }
    }
    
    nextOpt(args, Map())
  }

}
```


#### Installation


  This project currently lacks a public maven artifact, but can be 
installed locally after building via **mvn package**: 

```
mvn install:install-file \
  -Dpackaging=jar -DgroupId=com.trace3.util \
  -DartifactId=scala-parseopts -Dversion=0.1.5 \
  -Dfile=target/scala-parseopts-0.1.5.jar
```

The maven artifact for this dependency would then be:

```
  <dependency>
    <groupId>com.trace3.util</groupId>
    <artifactId>scala-parseopts</artifactId>
    <version>0.1.5</version>
  </dependency>
```



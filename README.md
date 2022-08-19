Scala Command Line Options Parser
=================================

## Overview

A Scala approach to parsing command-line options. This supports both 
long options (--longopt <val>) and short options (-o). 

Short options do not work combined (-asdf), but must be provided 
individually, eg. '-a -s -d -f'.  There is an additional restriction 
that short options are boolean switches only and do not take 
arguments. Long options, on the other hand, must have arguments.

Conveniently ParseOpts will additionally support non-options as long 
as they are provided last:
```
myApp -f -d --foo1 bar argX argY argZ  
```

To support this feature, options are returned as a
*scala.collection.immutable*.**Map** of options and **List** of
remaining arguments.

This approach works well for use with Apache Spark, and at the least, 
provides the flexibility of allowing options to be clearly defined 
and provided in any order.
```bash
#!/bin/bash
MYCLASS="com.foo.bar.example"
MYJAR="myexample-0.1.jar"

spark-submit --master yarn \
  $SPARK_CONF_ARGS \
  --class $MYCLASS \
  $MYJAR \
  $@
```

## Code

The code for performing the parsing is relatively straight-forward 
and consists primarily of the following scala function:
```scala
import scala.collection.immutable.{List, Map}

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

## Building 

Builds are Scala Version specific and provided as *profiles* to Maven. Currently 
supported Scala versions by profile:
 - scala-2.11
 - scala-2.12
 - scala-2.13

Build the *jar* file via `mvn package`
```
$ mvn package -Pscala-2.13
[ ... ]
INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ scala-parseopts ---
[INFO] Building jar: /home/tca/src/github/scala-parseopts/target/scala-parseopts-1.3.1_2.13.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  15.643 s
[INFO] Finished at: 2021-04-18T12:48:14-07:00
[INFO] ------------------------------------------------------------------------
```

Note that the POM currently sets the *artifactId* version with a variable which 
causes Maven to throw a *Warning* that `version` contains an expression. This 
is a known cross-compile issue with Scala binary versions and maven. 

This project was tested with the following versions:
- Maven 3.6.3 
- Java 1.8, Java 11
- Scala 2.11, 2.12, 2.13


## Using ParseOpts

The project has a GitHub based Maven Repository, which would need an entry 
to either maven settings or the project pom. However, this currently requires
authentication by GitHub.
```xml
  <repositories>
      <repository>
          <id>scala-parseopts</id>
          <url>https://maven.pkg.github.com/tcarland/scala-parseopts</url>
      </repository>
  </repositories>
```

Optionally create a local maven entry from the build of this repo
Note the scala binary version as described above, which should match the 
parent project.
```sh
mvn install:install-file \
  -Dpackaging=jar -DgroupId=com.trace3.util \
  -DartifactId=scala-parseopts -Dversion=1.4.1_2.13 \
  -Dfile=target/scala-parseopts-1.4.1_2.13.jar
```

The maven artifact for this dependency:
```xml
  <dependency>
      <groupId>com.trace3.util</groupId>
      <artifactId>scala-parseopts</artifactId>
      <version>1.4.1_2.13</version>
  </dependency>
```


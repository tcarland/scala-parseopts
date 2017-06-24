#!/bin/bash
#
# script for testing command-line options 
#
#
CMDOPTS_CLASS="com.trace3.util.ParseOpts"
CMDOPTS_JAR="target/scala-parseopts-0.1.2.jar"


scala -classpath $CMDOPTS_JAR $CMDOPTS_CLASS $@



 


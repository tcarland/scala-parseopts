#!/bin/bash
#
# spark-submit script for testing command-line options 
#
#
CMDOPTS_CLASS="com.trace3.spark.examples.OptionTest"
CMDOPTS_JAR="target/spark-parseopts-0.1.1.jar"


scala -classpath $CMDOPTS_JAR $CMDOPTS_CLASS $@

#eg.  --input somefile.txt --table myTable 50 51 52
 


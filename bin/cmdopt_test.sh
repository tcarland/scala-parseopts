#!/bin/bash
#
# spark-submit script for testing command-line options 
#
#
CMDOPTS_CLASS="com.trace3.spark.examples.OptionTest"
CMDOPTS_JAR="target/spark-parseopts-0.1.1.jar"


spark-submit --master yarn \
  --deploy-mode client \
  --num-executors 4 \
  --executor-memory 1g \
  --class $CMDOPTS_CLASS \
  $CMDOPTS_JAR \
  $@

#eg.  --input somefile.txt --table myTable 50 51 52
 


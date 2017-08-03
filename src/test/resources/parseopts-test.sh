#!/bin/bash
# 
SCALA_FILES="src/test/scala/ParseOptsTest.scala"
SCALA_CLASS="ParseOptsTest"

( mkdir -p target; scalac -d target $SCALA_FILES )

r=$?

if [ $r -eq 0 ]; then
    ( scala -cp target $SCALA_CLASS )
    r=$?
fi

exit $r

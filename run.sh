#!/bin/bash
#
# Use this shell script to compile (if necessary) your code and then execute it. Below is an example of what might be found in this file if your program was written in Python
#

# Getting Source Directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

# Compile the Java Program into Java classes
javac ${DIR}/src/H1bCounting.java

# Run program and pass the arguments to the Java classes
java -cp  ${DIR}/src H1bCounting ${DIR}/input/h1b_input.csv   


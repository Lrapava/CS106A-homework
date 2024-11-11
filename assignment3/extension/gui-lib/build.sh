#!/bin/bash

# clearing old data
rm -rf bin
rm -rf final

mkdir bin 
mkdir final 

# compiling
javac -d ./bin/ -cp ./src/:./lib/acm.jar ./src/*.java
jar cf ./final/gui-lib.jar -C ./bin .

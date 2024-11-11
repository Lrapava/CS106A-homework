#!/bin/bash

# clearing old data
rm -rf ./bin
rm -rf ./final

mkdir bin
mkdir final

# compiling
javac -d ./bin -cp ./src:./lib/acm.jar:./lib/gui-lib.jar ./src/*.java
jar cmf manifest.txt ./final/Breakout.jar -C bin .

# copying all necessary assets to execute the program to final
cp -r ./lib ./final/lib
cp -r ./assets ./final/assets

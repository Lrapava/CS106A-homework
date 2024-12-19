#!/bin/bash

# clearing old data
rm -rf ./bin
rm -rf ./final

mkdir bin
mkdir final

# compiling
javac -d ./bin -cp ./src:./lib/acm.jar ./src/*.java
jar cmf manifest.txt ./final/Hangman.jar -C bin .

# copying all necessary assets to execute the program to final
cp -r ./lib ./final/lib
cp -r ./assets ./final/assets

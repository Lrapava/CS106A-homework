#!/bin/sh

# installing dependencies
sudo apt update && sudo apt -y upgrade 
sudo apt -y install openjdk-17-jdk entr micro

# installing local stuff
./config.sh

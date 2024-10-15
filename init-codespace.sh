#!/bin/sh

# installing dependencies
sudo apt update && sudo apt upgrade
sudo apt install openjdk-17-jdk micro -y

# installing local stuff
./config.sh

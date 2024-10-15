#!/bin/sh

# configuring java
echo Please select java 17 as default java version
sudo update-alternatives --config java

# add libraries
mkdir -p ~/.local/lib/cs106a
cp ./lib/* ~/.local/lib/cs106a

# add custom scripts
mkdir -p ~/.local/bin
cp ./scripts/* /home/vscode/.local/bin

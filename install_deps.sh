#!/bin/sh

unsupportedPlatform() {
	echo It appears your platform is not supported.
	echo You will have to install dependecies manually.
}

# debian-based
sudo apt -y install openjdk-17-jdk entr  ||
# rhel-based
sudo dnf -y install java-17-openjdk entr || 
# arch-based
pacman -S jdk17-openjdk entr ||
# unknown
unsupportedPlatform

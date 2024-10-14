# update
sudo apt update
sudo apt upgrade -y

# installed packages
sudo apt install openjdk-17-jdk micro -y

# configure java
sudo update-java-alternatives --set /usr/lib/jvm/java-1.17.0-openjdk-amd64

# add custom scripts
mkdir /home/vscode/.local/bin
cp /workspaces/CS106A-codespace/scripts/karel /home/vscode/.local/bin

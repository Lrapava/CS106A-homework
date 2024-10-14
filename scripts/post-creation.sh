sudo apt update
sudo apt upgrade -y
sudo apt install openjdk-17-jdk -y
sudo update-java-alternatives --set /usr/lib/jvm/java-1.17.0-openjdk-amd64
mkdir /home/vscode/.local/bin
cp /workspaces/CS106A-codespace/scripts/karel /home/vscode/.local/bin

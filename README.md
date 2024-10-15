# Where did the interwebs bring me?

Had to take a programming methodology course at Free University of Tbilisi, which was heavily inspired by Stanford's CS106A.

I ended up using a text editor and some bash scripts to complete my homework instead of a full-blown IDE.

This repo is for people who would like to replicate a similar environment either on a Linux box or using GitHub Codespaces.

## Installing on Linux

After updating your system, run:

```bash

$ git clone https://github.com/Lrapava/CS106A-codespace

# ./install_deps.sh

$ ./config.sh

```

Supported Linux distributions: 
Ubuntu, Fedora, ArchLinux

In case of a different distribution, the user might have to install dependencies manually

## Utilizing GitHub Codespaces

Log into your GitHub account.

In the top right corner, click Code > Codespaces. After that, click on the plus sign to create a new Codespace.

Wait a few minutes for the Codespace to initialize (it might take quite some time). Once the Codespace is ready, switch to the TERMINAL tab and enter the following command (do not write the dollar sign): 

```bash
$ ./init_codespace.sh
```

After the command finnishes executing, switch to the PORTS tab and click on link near port 6080 while holding the Ctrl key. A new window should open. We'll call this window VNC Client window. In the newly opened browser window, click Connect and swith back to code editor.

## Usage

In this repository you will find 7 folders corresponding to 7 different homework assignments. In those folders there will be .java files, where you should write problem solutions.

To run your program, write the following command in the terminal:

```bash
$ runclass assignmentFolder/fileName.java
```

Replace assignmentFolder and fileName.java with fitting strings.

Alternatively, you could try using:

```bash
$ watchclass assignmentFolder/fileName.java
```

This command will run your program automatically every time you save the corresponding file, assuming it's not already running.

If you are using GitHub Codespaces, switch to VNC Client window to see your program running.

## Contributing & Known issues

Buttons are clickable but not rendered properly for assignment one. Not sure how to fix that.

Feel free to contribute to this project on GitHub if you feel like it.


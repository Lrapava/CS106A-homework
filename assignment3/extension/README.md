# Breakout extension

I tried limitting myself to only using syntax showcased on lectures & seminars when writing this extension.

Due to this fact, some things are not wrote as efficiently as they could have been.

I also basically wrote a mini GUI library (gui-lib) which relies on ACM in the process.

# Structure

`./gui-lib`   contains "library files"

`./breakout`  contains "files specific to the game"

`./united`    contains both in a less structured way

# Features

Additional features include:

* Custom tiny UI library
* Menu system
* FPS locking
* Velocity coefficient
* Framerate indicator
* Karma stat (chance of new ball spawning after a wall break)
* Compilation to `.jar` files for portability via custom build scripts

These features allow this program to be an accelent showcase of how FPS and velocity affect collision detection.

# Build instructions

You can compile the library using the provided build script (`./gui-lib/build.sh`)

After that, move the `./gui-lib/final/gui-lib.jar` file to `./breakout/lib`

Add `acm.jar` file to `./breakout/lib`

Compile Breakout using the provided build script (./breakout/build.sh)

Execute the `./breakout/file/Breakout.jar` file manually, or by running `./breakout/run.sh`

# Alternative build instructions

Go to `./united` and compile all the files provided in the directory

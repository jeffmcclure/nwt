[Return to README.md](README.md)

Information related to compiling nwt project from source.

# Requirements
* Java 17 or later

# Steps to compile
$ git clone https://github.com/jeffmcclure/nwt

$ cd nwt

#### build process will install compiled program in BIN_DIR if set
$ export BIN_DIR=$HOME

#### build and skip tests
$ ./gradlew build -x test

#### Test build
$ $HOME/nwt -h


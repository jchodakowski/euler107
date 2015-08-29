# euler107
This is my swipe at doing graph traversal on the cheap. I've made a number of choices to be 
code verbose and let the compiler clean it all up. I'm a right-brained person so I need to 
visualize my problems sometimes so there's extra code for that.

There is also data normalization code I felt was necessary because *production* and so there
are good and bad data sets in the commit.

## Quick Start
You can [download a compiled jar file from my Dropbox](https://www.dropbox.com/s/uuzad01j1jhcev9/euler_p107.jar?dl=0)

To use or test this project, you can take the following steps:
```
git clone https://github.com/jchodakowski/euler107.git
cd euler107
wget https://www.dropbox.com/s/uuzad01j1jhcev9/euler_p107.jar?dl=0
java -cp euler_p107.jar EdgeDetector
```

The main executable assumes the data ('p107_network.txt') is in the working path. You can provide either a full path or
a bare word as an argument to provide alternate datasources. You can test this with some included bad input data.
```
java -cp euler_p107.jar EdgeDetector p107_network_bad.txt
```

## Compiling
This project is a single class with a bare (default) package. To compile the project, take the following steps:
```
git clone https://github.com/jchodakowski/euler107.git
cd euler107
javac src/EdgeDetector.java
java -cp src/ EdgeDetector
```

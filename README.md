# euler107
This is my swipe at doing graph traversal on the cheap. I've made a number of choices to be 
code verbose and let the compiler clean it all up. I'm a right-brained person so I need to 
visualize my problems sometimes so there's extra code for that.

There is also data normalization code I felt was necessary because *production* and so there
are good and bad data sets in the commit.

This code lives in the default package so can be executed as so:
```
java -cp . EdgeDetector
```

That pattern will require you to have the data file 'p107_network.txt' in your current path
as well as the compiled class file. The main execution block will accept the file name/path
as an argument, and you can test a bad-data example like so:
```
java -cp . EdgeDetector p107_network_bad.txt
```

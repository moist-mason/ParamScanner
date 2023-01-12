## Param Scanner ##

Simple program that parses a java source directory and outputs a CSV file containing a list of every param for each method
in every class. Works for both normal methods and constructors.

Primarily intended as a complementary tool for the AncientCoderPack, but it can be used for any purpose. Note that currently 
it only parses through the first class in a given Java file (so the outermost one). This may change in the future.

This library uses QDox for java file parsing, so the input cannot be a JAR or ZIP file. It has to be a source directory containing 
decompiled or not-yet compiled Java code (preferably the src/main/java path in your project of choice, or whatever folder 
acts as the root of your java packages).

### Usage ###

```
java -jar paramscanner.jar --srcDir {SRC_DIRECTORY} --csv {CSV_PATH} 
```

### Specific Example ###
If a method named xyz in Class Example is called as such:

```
public String xyz(int valueA, String valueB)
```
Then the parameters will be listed in the CSV file as such: 
```
name,method,method descriptor,class
valueA,xyz,(int;String;) ret String,Example
valueB,xyz,(int;String;) ret String,Example
```

The CSV file included in the example folder was generated using GSON.
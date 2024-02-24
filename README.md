# ApexStats
Description 
This is a Java app that will be a showcase of my Apex Stats 

Dependencies 
===============================================================
Java version 17 
https://www.oracle.com/java/technologies/downloads/

JavaFX Version 17 
https://openjfx.io/

Apache Maven
https://maven.apache.org/download.cgi?.

===============================================

Running the project after cloning 
-------------------------------------------------------------
terminal - within project directory 

Compile project 
mvn package 

Run Project 
```bash
java --module-path /your/path/to//javafx-sdk-17.0.7/lib --add-modules javafx.controls,javafx.fxml -cp target/ApexStats-1.0-SNAPSHOT-jar-with-dependencies.jar ApexStatsApp
```
or
```bash
java --module-path /your/path/to//javafx-sdk-17.0.7/lib --add-modules javafx.controls,javafx.fxml -cp target/ApexStats-1.0-SNAPSHOT.jar ApexStatsApp
```
--------------------------------------------
***** Lastly there is a resources file in the root directory. YOU NEED TO ADD A config.yaml there 
username: ****
api_key: ************
platform: ***






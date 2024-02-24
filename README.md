# ApexStats

## Description 

This is a Java app that will be a showcase of my Apex Stats. 

## Dependencies

Java version 17: <https://www.oracle.com/java/technologies/downloads/>

JavaFX Version 17.0.10: <https://openjfx.io/>

Apache Maven 3.9.6: <https://maven.apache.org/download.cgi?>

## Configure YML (config.yml) with contens below

To generate an API key: <https://portal.apexlegendsapi.com>

username: ****
api_key: ************
platform: ***

## Running the project after cloning 
cd into the project 

Compile project
``` bash
mvn package
```

Run Project 
```bash
java --module-path </PATH/TO>/javafx-sdk-17.0.7/lib --add-modules javafx.controls,javafx.fxml -cp target/ApexStats-1.0-SNAPSHOT-jar-with-dependencies.jar ApexStatsApp
```
or
```bash
java --module-path <PATH/TO>/javafx-sdk-17.0.7/lib --add-modules javafx.controls,javafx.fxml -cp target/ApexStats-1.0-SNAPSHOT.jar ApexStatsApp
```
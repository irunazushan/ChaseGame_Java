#!bin/bash

cd ChaseLogic
mvn clean package

cd ..
cd Game
mvn clean package
cd ..

mv Game/target/Game-1.0-SNAPSHOT-jar-with-dependencies.jar game.jar

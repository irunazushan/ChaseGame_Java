#!bin/bash

cd ChaseLogic
mvn clean

cd ..
cd Game
mvn clean
cd ..

rm game.jar

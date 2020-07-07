#!/bin/sh

java -jar ../target/challenges-jar-with-dependencies.jar Application --config $1 --url $2 --username $3 --password $4

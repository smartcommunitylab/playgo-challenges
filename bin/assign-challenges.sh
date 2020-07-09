#!/bin/sh


java -jar ${CHALLENGE_LIB_HOME:-..}/target/challenges-jar-with-dependencies.jar Application --config $1 --url $2 --username $3 --password $4 --assign ${5-""}

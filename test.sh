#!/bin/sh

while [ 1 ]
do
    mvn clean test
    inotifywait -e close_write `find src/ -type f ! -name '*.swp'`
done

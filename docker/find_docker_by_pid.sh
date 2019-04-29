#! /bin/bash

pid=$1
containers=`docker ps | awk '{print $1}'`
containers=(${containers// / })
for i in $(seq 1 $((${#containers[@]}-1)));
do
    cid=${containers[$i]}
    echo $i $cid
    docker top $cid | grep $pid
done

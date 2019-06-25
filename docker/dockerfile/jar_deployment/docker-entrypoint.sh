#! /bin/bash

set -eux

if [ -f "/home/data/logs/aaa.out" ]
then
  rm /home/data/logs/*.out
fi

log_aaa=/home/data/logs/aaa.out
log_bbb=/home/data/logs/bbb.out

log_aaa_file_size=1024

nohup java -jar /root/aaa.jar > $log_aaa 2>&1 &

filesize=`ls -l $log_aaa | awk '{ print $5 }'`
while [ $filesize -lt $log_aaa_file_size ]
do
  sleep 1
  filesize=`ls -l $log_aaa | awk '{ print $5 }'`
done

nohup java -jar /root/bbb.jar > $log_bbb 2>&1 &

exec "$@"

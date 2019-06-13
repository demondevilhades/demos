
docker run -itd \
--privileged -e container=docker \
--name centos76-base \
-v /data/common:/data/common \
-p 10001:22 ^
centos:latest /usr/sbin/init

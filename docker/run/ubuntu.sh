
docker run -itd \
--name ubuntu18-test \
-v /data/common:/data/common \
-p 10001:22 \
ubuntu:18.04


#docker

#########start

#for cpu
docker run -i -t \
-e SSHD=Y 
--name java8-1 \
-v /data:/data \
-p 10001:22 \
-p 10002:80 \
-p 10003:6006 \
-p 10004:8888 \
-p 10005:8080 \
-v /etc/localtime:/etc/localtime:ro \
-d ubuntu18-java8:v1.0

#for gpu
docker run -i -t \
-e SSHD=Y 
--name tf1.13-py3-gpu \
-v /data:/data \
-p 10001:22 \
-p 10002:80 \
-p 10003:6006 \
-p 10004:8888 \
-p 10005:8080 \
-v /etc/localtime:/etc/localtime:ro \
-d --runtime=nvidia tensorflow/tensorflow:1.13.1-gpu-py3-jupyter

#########run






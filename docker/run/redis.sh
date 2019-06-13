
docker run -itd \
--name redis-test \
-p 10001:22 \
-p 10002:6379 \
redis:5 \
--restart=always redis-server \
--requirepass "root"

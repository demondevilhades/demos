
docker run -itd \
--name nginx-test \
--link docker_container_name:alias \
-v ***/default.conf:/etc/nginx/conf.d/default.conf \
-p 10001:22 \
-p 10002:80 \
nginx:latest

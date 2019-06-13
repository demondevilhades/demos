
docker run -itd \
--name mysql-test \
-e MYSQL_ROOT_PASSWORD=root \
-v ***/mysql:/var/lib/mysql \
-p 10001:22 \
-p 10002:3306 \
mysql:latest \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci 

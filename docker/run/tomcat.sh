
docker run -itd \
--name tomcat8-test \
-v ***\logs:/usr/local/tomcat/logs \
-v ***\temp:/usr/local/tomcat/temp \
-v ***\work:/usr/local/tomcat/work \
-p 10001:22 \
-p 10002:8080 \
tomcat:8

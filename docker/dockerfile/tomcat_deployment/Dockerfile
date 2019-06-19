
# FROM tomcat:latest 
FROM tomcat:8.5.42-jdk8 

MAINTAINER *** <***@***> 

# copy project 
RUN mkdir /usr/local/tomcat/webapps/*** 
COPY ./*** /usr/local/tomcat/webapps/*** 

VOLUME ["/usr/local/tomcat/logs", "/usr/local/tomcat/temp", "/usr/local/tomcat/work"] 

# ssh 
#RUN set -eux; \
#  apt-get update; \
#  apt-get install -y --no-install-recommends openssh-server; \
#  sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config; \
#  sed -i 's/#X11UseLocalhost yes/X11UseLocalhost no/' /etc/ssh/sshd_config \
#
#EXPOSE 22

# CMD ["/etc/init.d/ssh", "start"] 

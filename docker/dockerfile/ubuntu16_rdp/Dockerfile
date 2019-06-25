
FROM ubuntu:16.04

MAINTAINER awesome

COPY ./sources.list /etc/apt/sources.list.aliyun
RUN set -eux \
  && cp /etc/apt/sources.list /etc/apt/sources.list.bak \
  && cp /etc/apt/sources.list.aliyun /etc/apt/sources.list \
  && apt-get update \
  && apt-get install -y --no-install-recommends vim screen \
# ssh 
  && apt-get install -y --no-install-recommends openssh-server \
  && sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
  && sed -i 's/#X11UseLocalhost yes/X11UseLocalhost no/' /etc/ssh/sshd_config \
# rdp 
  && apt-get install -y --no-install-recommends xfce4 xrdp vnc4server \
  && touch ~/.xsession \
  && echo "xfce4-session" > ~/.xsession \
# install xubuntu-desktop with default 
  && cp /etc/apt/sources.list.bak /etc/apt/sources.list \
  && apt-get update \
  && apt-get install -y --no-install-recommends xubuntu-desktop \
# switch to aliyun 
  && cp /etc/apt/sources.list.aliyun /etc/apt/sources.list \
  && apt-get update 

EXPOSE 22
EXPOSE 3389

# docker build -t ubuntu16_rdp:1.0 .
# docker run -itd --name u-test -v ./docker-entrypoint.sh:/docker-entrypoint.sh -p 20001:22 -p 20002:3389 ubuntu16_rdp:1.0 /docker-entrypoint.sh /bin/bash
# docker exec -it u-test passwd

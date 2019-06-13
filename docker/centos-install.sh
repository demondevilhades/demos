
# 1. install tools
yum install -y yum-utils device-mapper-persistent-data lvm2

# 2. add repo
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 3. check repo
yum list docker-ce --showduplicates | sort -r

# 4. install docker
yum install docker-ce

# 5. check docker
docker version

# start docker
systemctl start docker

# use docker
sudo gpasswd -a user docker
newgrp - docker

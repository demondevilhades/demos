#! /bin/bash

set -e
if [ $# -gt 0 ]; then
  apt update -y && apt install -y openssh-server && passwd
  if [ -x '/run_jupyter.sh' ]; then
    sed -i '$ish /forssh.sh' /run_jupyter.sh
  else
    sed -i '$ash /forssh.sh' /etc/bash.bashrc
  fi
fi

if [ $SSHD ]; then
  if [ -x '/usr/sbin/sshd' ]; then
    if [ ! -d '/run/sshd' ]; then
      mkdir /run/sshd
      sed -i 's/prohibit-password/yes/' /etc/ssh/sshd_config
      echo 'X11UseLocalhost no' >> /etc/ssh/sshd_config
    fi
    if [ $(ps aux | grep sshd | wc -l) -gt 0 ]; then
      /usr/sbin/sshd
    fi
  fi
fi

#! /bin/bash

if [ $SSHD ]; then
  if [ -x '/usr/sbin/sshd' ]; then
    if [ ! -d '/run/sshd' ]; then
      mkdir /run/sshd
      sed -i 's/prohibit-password/yes/' /etc/ssh/sshd_config
      echo 'X11UseLocalhost no' >> /etc/ssh/sshd_config
    fi
    /usr/sbin/sshd
  fi
fi

jupyter notebook "$@"

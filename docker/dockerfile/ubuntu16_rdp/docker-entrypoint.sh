#! /bin/bash

set -eux

if [ -f "/run/xrdp/xrdp.pid" ]; then
  rm "/run/xrdp/xrdp.pid"
fi

if [ -f "/run/xrdp/xrdp-sesman.pid" ]; then
  rm "/run/xrdp/xrdp-sesman.pid"
fi

/etc/init.d/xrdp restart

if [ -x '/usr/sbin/sshd' ]; then
  if [ $(ps aux | grep sshd | wc -l) -gt 0 ]; then
    /usr/sbin/sshd
  fi
fi

exec "$@"

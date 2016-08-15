#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^C:/Android/eclipse/lib/cygwin/lib!/usr/lib!ig;s! C:/Android/eclipse/lib/cygwin/lib! /usr/lib!ig;s!^C:/Android/eclipse/lib/cygwin/bin!/usr/bin!ig;s! C:/Android/eclipse/lib/cygwin/bin! /usr/bin!ig;s!^C:/Android/eclipse/lib/cygwin/!/!ig;s! C:/Android/eclipse/lib/cygwin/! /!ig;s!^Y:!/cygdrive/y!ig;s! Y:! /cygdrive/y!ig;s!^D:!/cygdrive/d!ig;s! D:! /cygdrive/d!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi

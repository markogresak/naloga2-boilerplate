#!/bin/bash

# podatki za povezavo na lalgec, lahko je tudi kar host iz ssh configa
lalgec_connect="uXXXXXXXX@lalgec.fri.uni-lj.si"

if [[ "$lalgec_connect" == "uXXXXXXXX@lalgec.fri.uni-lj.si" ]]; then
  echo "Podatki za povezavo na lalgec niso nastavljeni!"
  exit 1
fi

# nalozi source na lalgec
scp ./src/Naloga2.java "$lalgec_connect:"
# pozeni ukaz za oddajo
ssh "$lalgec" "oddajAPS2 Naloga2 <<< da"

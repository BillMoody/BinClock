#!/bin/bash

# build
ant clean
ant

now=$(date +'%Y%m%d%H%M%S')

# coipy files
dest=~/binClock
if [ -d $dest ]; then
  echo "Moving $dest to $dest.$now"
  mv $dest $dest.$now
fi

mkdir $dest

cp run.sh $dest
cp dist/BinaryClock.jar $dest

cd $dest
chmod 755 run.sh
# ./run.sh


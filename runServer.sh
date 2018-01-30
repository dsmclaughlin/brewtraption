# Simple script to unpack the server distribution and execue it with no parameters specified
#!/bin/bash
#set -x

mkdir -p server-dist
cp target/brewtraption-dist.tar.gz server-dist
tar -zxf server-dist/brewtraption-dist.tar.gz -C server-dist/
cd server-dist/brewtraption/
pwd
java -jar brewtraption.jar

# Simple script to unpack the server distribution and execue it with no parameters specified
#!/bin/bash
set -x
mkdir -p server-dist
cp target/brewtraption-dist.tar.gz server-dist
tar -zxf server-dist/brewtraption-dist.tar.gz -C server-dist/
java -jar server-dist/brewtraption/brewtraption.jar

#!/usr/bin/env bash

cur_dir=$(basename "$PWD")
if [[ ${cur_dir} == "bin" ]]; then
  cd ..
fi

mkdir -p target/thrift/
rm -fr target/thrift/*
for file in src/main/thrift/* ; do
    echo "generate ${file}"
    thrift --gen java:generated_annotations=undated -out target/thrift/ ${file}
done

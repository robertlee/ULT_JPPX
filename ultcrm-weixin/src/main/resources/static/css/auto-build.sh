#!/bin/bash
echo "开始构建: lessc common.less common.css"
while true
do
echo "开始构建.."
lessc common.less common.css
echo "构建完成"
sleep 15s
done

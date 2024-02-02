#!/bin/bash

# 此脚本用于产生发送的metrics，用来帮助测试Grafana的PromSQL。

# 定义多个API
apis=(
    'http://localhost:8080/metrics/api-0'
    'http://localhost:8080/metrics/api-1'
    'http://localhost:8080/metrics/api-2'
    'http://localhost:8080/metrics/api-3'
    'http://localhost:8080/metrics/api-4'
    'http://localhost:8080/metrics/api-5'
    # 添加更多URL...
)

# 循环处理每个URL
while true
do
    # 从apis数组中随机选择一个API
    random_api=${apis[$((RANDOM % ${#apis[@]}))]}

    # 发送curl请求
    curl --location "$random_api"
    # 空行
    echo

    # 生成随机的毫秒数（在100到1000之间，可以根据需要调整）
    random_milliseconds=$((5 + RANDOM % 10))

    # 暂停脚本执行，转换为秒
    sleep $(bc <<< "scale=3; $random_milliseconds / 1000")
done

#!/bin/bash

# 此脚本用于产生发送的metrics，用来帮助测试Grafana的PromQL。

# 定义多个URL及其对应的概率，按照不同的概率产生指标
declare -a urls=(
    'http://localhost:8080/metrics/api-1 40'
    'http://localhost:8080/metrics/api-2 30'
    'http://localhost:8080/metrics/api-3 20'
    'http://localhost:8080/metrics/api-4 5'
    'http://localhost:8080/metrics/api-5 5'
)

while true
do
    # 生成随机数
    rand=$((RANDOM % 100))

    # 根据概率加权随机选择API
    selected_url=""
    total_probability=0
    for api in "${urls[@]}"; do
        url=${api% *}
        probability=${api#* }
        total_probability=$((total_probability + probability))
        if ((rand < total_probability)); then
            selected_url=$url
            break
        fi
    done

    # 发送curl请求
    curl --location "$selected_url"
    echo

    # 生成随机的毫秒数（在100到1000之间，可以根据需要调整）
    random_milliseconds=$((5 + RANDOM % 10))

    # 暂停脚本执行，转换为秒
    sleep $(bc <<< "scale=3; $random_milliseconds / 1000")
done
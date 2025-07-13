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

    # 生成随机的Header参数
    random_user_id="User-$((RANDOM % 9000 + 1000))"  # 随机生成用户ID，范围是1000到9999
    random_session_id="Session-$((RANDOM % 9000 + 1000))"  # 随机生成会话ID，范围是1000到9999
    random_language="en-US"  # 默认语言（可以随机化）

    # 发送curl请求，包含随机的header
    curl --location "$selected_url" \
         -H "X-Random-User-ID: $random_user_id" \
         -H "X-Random-Session-ID: $random_session_id" \
         -H "Accept-Language: $random_language"
    echo

    # 生成随机的毫秒数（在100到1000之间，可以根据需要调整）
    random_milliseconds=$((5 + RANDOM % 10))

    # 暂停脚本执行，转换为秒
    sleep $(bc <<< "scale=3; $random_milliseconds / 1000")
done
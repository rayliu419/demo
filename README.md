# demo
演示spring boot程序。

# 运维
- 集成Loki和Prometheus.
- 练习PromQL和LogQL

# How to Start 
1. 启动依赖。
- docker-compose up
2. 启动应用
- App没有通过docker来运行。
- 因为App经常需要修改。
3. 运行[generateMetrics.sh](docker%2FgenerateMetrics.sh)
- 随机产生metrics。
- 打印随机日志用来实验LogQL。
4. 随机生成Log。
5. grafana看板观测指标和Log.
- 配置数据源
- http://localhost:3000/connections/datasources
6. 练习LogQL。
- {host="liuruideMacBook-Pro.local"}
7. 练习PromQL
- sum(rate(http_server_requests_seconds_count{job="demo"}[1m])) by (uri)
8. grafana的[grafana_volume](docker%2Fgrafana_volume)
- 不要删除，否则会删除掉已有的看板。
redis:
47.96.130.15:6379




Data monitoring & analysis
数据监控及分析

`服务器操作`

``MySql安装``

wget http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm &&
yum -y install mysql57-community-release-el7-10.noarch.rpm &&
yum -y install mysql-community-server

``问题解决办法``

```key问题```
1. rpm -import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
2. yum -y install mysql-community-server

``启动MySql服务``

systemctl start mysqld.service

```查看mysql初始密码```

grep "password" /var/log/mysqld.log



启动zookeeper
启动kafka 之前需要先启动zookeeper 否则会报错
启动命令：
nohup ./bin/zookeeper-server-start.sh config/zookeeper.properties &
-daemon：表示以守护进程的方式启动。

启动 kafka
启动命令：
nohup ./bin/kafka-server-start.sh -daemon config/server.properties
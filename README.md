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
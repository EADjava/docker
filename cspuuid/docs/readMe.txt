一. 待实现
1. 性能测试
    验证支持的最大并发(对应调整tomcat线程参数)
    验证合理的redis连接数
    验证资源消耗

2. redis宕机, 当前key值恢复补偿机制


3. 服务熔断机制

4. swagger和confluence接口文档


二. 已经实现
1. redis唯一ID
2. 重载UUID服务
3. UUID规范
    1)redis key命名: CSPUUID:BIZ:业务系统标识, 默认为: CSPUUID:BIZ:JY
    2)UUID最大长度为20位, 包括三部分: 业务系统表示+年月日(YYMMDD)+8位数字
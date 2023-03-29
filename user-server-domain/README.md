# 用户领域服务
> 如果性能问题可以继续按读写维度继续拆分微服务。

进入领域层通常都包含分片键。

## 职责
- 查询用户信息
- 注册用户
- 验证密码
- 操作用户DB

## 参考文档
dubbo配置手册: https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/config/properties/#consumer
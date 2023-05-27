# 亿级用户中心实战

该项目采用云原生的基础设施完成用户中心建设。

## 整体架构图

- 两地四中心：数据层保证在存在同城的两个中心，亿级一个异地（异步）中心；
- 数据分片：按用户尾号分片，DB分片不一定需要和逻辑层一致；随着数据增长DB会继续拆，拆分方法为百库十表；
- 容灾处理：同城两IDC互为灾备，异步容灾需要核对数据后切换；
  ![img.png](doc/arch.png)

## 开发规范

### 模块划分

| 模块                        | 功能     | 错误码前缀 | 部署方案     |
|---------------------------|--------|-------|----------|
| protocol                  | 协议管理   | -     | -        |
| user-server-app           | 用户应用层  | 20000 | user-set |
| user-server-domain        | 用户领域层  | 21000 | user-set |
| middleware-distributed-id | 分布式ID  | 90000 | idc      |
| external-gateway          | 入口业务网关 | 91000 | idc      |
| internal-gateway          | 内网业务网关 | 92000 | idc      |
| db-proxy                  | DB代理   | -     | idc      |

set命名：业务加set+两位编号，十位基本代表城市，同一个城市用一个数字。
约定0：深圳，1：上海

### 用户SET 划分

两地4中心架构，深圳上海两地流量均分。

- user-set00:用户尾号00-24
- user-set01:用户尾号25-49
- user-set10:用户尾号50-74
- user-set11:用户尾号75-99

### 数据分片

**分片规则**  
以用户尾号后3位确定分片规则，初期采用百库十表逻辑。

部署4套主DB,后期可以DB层自己扩，数据分片与set分片是解耦的，但要保证set和db分片城市级对应关系。

user-db00: 处理尾号00-24
user-db01: 处理尾号25-49
user-db10: 处理尾号50-74
user-db11: 处理尾号75-99

```text
例uid: xxxx351
后两位确定DB为user-db10,百位确定表为3号表
```

### 调用规范

待补

## K8s 环境

### 安装

实验安装使用简易版本[Rancher Desktop](https://rancherdesktop.io/)

### Dashboard 安装

[https://github.com/kubernetes/dashboard](https://github.com/kubernetes/dashboard)

```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
kubectl proxy
```

[访问](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)

## 访问token

https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md

```shell
# 创建用户
kubectl apply -f dashboard-adminuser.yaml
# 生成token 可以多次，默认有效期较短，可以参数定制有效期
kubectl  create token admin-user -n kubernetes-dashboard [--duration 10h|m|s]
```

### 常用支持

**自助查询**

```shell
kubectl -h
kubectl get -h
# 常用命令总结
# 支持缩写 个人习惯全写免得混淆 deployments == deploy nodes == no services == svc
kubectl get all -n namespace
kubectl get deployments|nodes|pods|services|rs|configMap [name]  [-A 全部空间] [-n 指定空间] -o wide [--show-labels 展示标签] 
kubectl describe deployments|nodes|pods|services|rs|configMap  name -n 空间
kubectl delete deployments|nodes|pods|services|rs|configMap name -n namespace
kubectl edit deployments|nodes|pods|services|rs|configMap name -n namespace
kubectl exec pod名称 -it -n 空间 -- bash(命令)
kubectl logs podname [-f] -n 空间 [-p 销毁了的前一个日志]
# 创建
kubectl apply -f  config_map.yaml deployment.yaml service.yaml
# 删除
kubectl delete -f  deployment.yaml
# 重建
kubectl get pod mypod -o yaml -n 空间  | kubectl replace -f -
# 重启
kubectl rollout  restart deployment|daemonset/name -n 空间
# 临时测试下pod或service 中的端口
kubectl port-forward pod|service/name -n 空间  --address 0.0.0.0 主机端口:pod或service端口
```

### 创建项目命名空间

命名：`user`

```shell
# 创建命名空间
kubectl apply -f namespace.yaml
```

**常用命令**

```shell
# 查看命名空间
kubectl get namespaces
# 获取指定命名空间
kubectl get namespaces <name>
# 获取命名空间详细信息
kubectl describe namespaces <name>
# 删除命名空
kubectl delete namespaces <insert-some-namespace-name>
# 创建命名空间
kubectl apply -f namespace.yaml
```

### node 规划

通常我们有很多node,需要给node设置标签，让我们的pod根据标签选择合适的node.
> 当前实验环境只有一个(node 名字默认lima-rancher-desktop)，我们也给它指定一些标签
> 创建node标签

```shell
kubectl label nodes lima-rancher-desktop city=sz
kubectl label nodes lima-rancher-desktop idc=sz-idc1
# 部署应用
kubectl label nodes lima-rancher-desktop layer=application
```

**常用命令**

```shell
# 查看node 标签
kubectl get nodes --show-labels
# 给node 添加标签
kubectl label nodes <your-node-name> <key1>=<value> <key2>=<value>
# 删除node 标签
kubectl label nodes <your-node-name> <key1>- <key2>- 
```

## 部署应用

配置手册大全
https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.27/

### external-gateway 外网网关

> 按IDC部署

k8s service: external-gateway-${idc}.user

```shell
# 可以处理模版
export idc=sz-idc1 &&  envsubst < external-gateway.yaml | kubectl apply -f -
# 使用这种需要手动修改模版汇总占位符
kubectl apply -f external-gateway.yaml
```

- 创建configMap：挂载配置
- 创建deployment：部署
- 创建service：使用DNS访问(servcie.namespace)，`external-gateway.user`

### internal-gateway 内网网关

> 按IDC部署

k8s service: internal-gateway-${idc}.user

```shell
# 可以处理模版
export idc=sz-idc1 &&  envsubst < internal-gateway.yaml | kubectl apply -f -
# 使用这种需要手动修改模版汇总占位符
kubectl apply -f internal-gateway.yaml
```

### middleware-distributed-id 分布式ID

> 按IDC部署

k8s service: middleware-distributed-id-${idc}.user

```shell
# 可以处理模版
export idc=sz-idc1 &&  envsubst < middleware-distributed-id.yaml | kubectl apply -f -
# 使用这种需要手动修改模版汇总占位符
kubectl apply -f middleware-distributed-id.yaml
```

### user-server-app 用户领域服务

> 按SET部署

k8s service: user-server-app-${set}.user

```shell
# 可以处理模版
export idc=sz-idc1 && export set=user-set00  &&  envsubst < user-server-app.yaml | kubectl apply -f -
# 使用这种需要手动修改模版汇总占位符
kubectl apply -f user-server-app.yaml
```

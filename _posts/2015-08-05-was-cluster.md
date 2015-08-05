---
layout: post
title: was集群安装配置部署
category: java
comments: false
---

##集群架构
一个管理节点2个受管节点
3个节点分别部署到3台服务器

<table>
<tr>
<td></td>
<td>主机名</td>
<td>IP</td>
</tr>
<tr>
<td>管理节点</td>
<td>mngr</td>
<td>129.168.0.10</td>
</tr>
<tr>
<td>节点1</td>
<td>app1</td>
<td>192.168.0.11</td>
</tr>
<tr>
<td>节点2</td>
<td>app2</td>
<td>192.168.0.12</td>
</tr>
</table>

##安装方法

### mngr服务器上was安装
选择安装was环境时选择`Management`
### app1服务器上was安装
选择安装was环境时选择`Application Server`或`Customer`
### app2服务器上was安装
同app1

### 启动dmgr服务

```
/opt/IBM/WebSphere/AppServer/profiles/Dmgr01/bin/startManager.sh -profileName=Dmgr01
```

启动成功后可在浏览器中访问`http://localhost:9060/ibm/console`进入管理控制台

### 将app1和app2节点托管给dmgr节点

```
/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/addNode.sh 192.168.0.10:8879
```

### 启动was1和was2的代理服务(nodeagent)

```
/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/startNode.sh
```

###配置集群

+ 登录管理控制台
+ 新建集群服务器取名`cluster1`(注意勾选配置HTTP会话内存到内存的复制)
+ 对应两台服务器分别添加一个server，取名`was1`和`was2`
+ 到系统管理->节点->同步节点
+ 启动集群

###部署应用

+ `应用程序` -> `新建应用程序` -> `下一步...` -> `was7注意填写应用版本号` -> `如果有多个应用服务器还应选择部署的服务器` -> `填写应用程序根` -> `完成`

+ `应用程序` -> `版本控制中心` -> `转出`

---
layout: post
title: EIP总结
category: work
comments: false
---
---
# portal bug修改
+ [x] portal 在本地测试机上springcurity报异常  
解决：
修改`web.xml`文件,将`classpath:`改为`/WEB-INF/classes/`

```
<context-param>
	  <param-name>contextConfigLocation</param-name>
	  <param-value>/WEB-INF/classes/applicationContext*.xml</param-value>
</context-param>
```
---
# portal 优化
## 将portal源代码加入svn库
+ [x] 原portal源代码版本管理混乱不完整，将整理后的portal源码加入svn，portal新分支:

 - [dataapp_20150305](https://10.137.80.91:6103/svn/root/EIP/EIP2/dwcode/portal-java/dataapp_20150305) portal主程序，web应用
 - [ReportDispatch_20150305](https://10.137.80.91:6103/svn/root/EIP/EIP2/dwcode/portal-java/ReportDispatch_20150305) 报表调度主程序，web应用
 - [ShellShedule_20150305](https://10.137.80.91:6103/svn/root/EIP/EIP2/dwcode/portal-java/ShellShedule_20150305) 报表调度脚本，jar应用

## 菜单查询逻辑优化

+ [x] 增加3个视图：

 1. `V_SYS_R_ROLE_RIGHT`角色权限关联视图
 2. `V_SYS_RIGHT`权限视图（整合了菜单和报表）
 3. `V_SYS_RIGHT_TREE`权限树

+ [x] 修改查询逻辑由`循环遍历查询`改为`一次查询`

## 其他sql修改
+ [x] delete语句  
`delete from TABLE where 1=1 [and (...)]`
改为  
`delete from TABLE where 1=0 [or (...)]`  
_建议`逻辑删除`,慎用`物理删除`_

---
# portal 需求变更

## 首页
+ [x] 欢迎页改成默认模块
+ [x] 首页没权限的模块不显示（原来置灰）
+ [x] 顶部菜单只显示一级

## 标准化管理模块
+ [x] 新增指标改成不需要申请单也可以新增
+ [x] 新增维度做成不选择申请单可以新增
+ [x] 指标信息加入KPI分类
+ [x] 流程申请中返回按钮回到历史页面
+ [x] 申请单附件丢失
+ [x] 代办申请单默认状态禁用改为启用

## OA指标字典
+ [x] 指标字典改为和portal一套程序，同数据库  
<font size=1>_注意：portal中针对oa指标字典新增加了一个`indicatorListPub.action`此页面对外开放，为了保证oa指标字典链接不变，更新时需要将`indicatorListPub.action` 改为`indicatorList.action`参考`applicationContext-security-basic.xml`片段：_</font>  

由

```
<!-- 指标字典开放 -->
<s:intercept-url pattern="/system/indicator/indicatorListPub.action" filters="none" /> 
<s:intercept-url pattern="/system/indicator/getIndicatorListPub.action" filters="none" /> 
<s:intercept-url pattern="/system/indicator/getHistoryByIdPub.action" filters="none" />
```

改为

```
<!-- 指标字典开放 -->
<s:intercept-url pattern="/system/indicator/indicatorList.action" filters="none" /> 
<s:intercept-url pattern="/system/indicator/getIndicatorListPub.action" filters="none" /> 
<s:intercept-url pattern="/system/indicator/getHistoryByIdPub.action" filters="none" />
```


`struts-standardization.xml`片段：

由

```
<action name="indicatorList" class="indicatorAction" method="indicatorList">
    <result name="success">/pages/standardization/indicator/list.jsp</result>
</action>
```

改为

```
<action name="indicatorList" class="indicatorAction" method="indicatorList">
    <result name="success">/pages/standardization/indicator/list1.jsp</result>
</action>
```

+ [x] 标准化指标字典隐藏已禁用指标

## cognos 集成
+ [x] 增加url查询参数
+ [x] cognos登录改为form表单模式


## 数据管理模块
+ [x] 数据导入附件上传，数据存到180.1
+ [x] 修改数据导入排序

## 增加内容发布模块
+ [x] 客户经营管理报表
+ [x] 历史报表



---
# portal 后续工作

##导入菜单和用户角色 (★★★★★)

cognos服务器不迁移完成此步骤portal服务器基本可用（缺少报表监控部分功能）

- [ ] 菜单可整理成xls统一导入数据库（注意，id由序列生成，导入数据后需要修改序列的当前值）
- [ ] 用户角色可整理成xls导入数据库同时注意序列问题
- [ ] 新服务器未开通数据库客户端直连权限，可用web客户端（jdbexplorer）,系统上线后该客户端可考虑卸载

##cognos监控日志改进 (★★★★☆)

cognos服务器不迁移完成此步骤portal服务器可用

- [ ] `CubeMonitorAction.java` -> `down()`文件直接从本地读取，改成远程读取可参考的实现方式：

  1. ~~网络映射,80.4共享一个文件夹，portal通过形如`//10.129.80.4/share/aaa.log`的方式访问文件~~
  2. ~~ftp,80.4启动ftp服务，并开放端口给82.10,portal通过java ftp访问~~
  3. http,80.4开放对log日志文件夹的web访问权限，可在`cognos（80.4）`所在的tomcat下部署一个用于下载的web应用对外提供http下载功能

## cognos集成 (★★★☆☆)
- [ ] cognos登录存在跨域安全性问题

  1. portal和cognos服务器使用同一个域名
  如`http://taikang.com/dataapp/`和`http://taikang.com/p2pd/servlet/dispatch/`
  2. ~~客户端添加信任站点~~
  3. 采用url传参数的方式（类似领导驾驶舱）
  
- [ ] 修改cognos服务器地址 ，`系统管理` -> `门户维护` -> `报表维护` -> `报表服务器地址维护` -> 启用
- [ ] 修改领导驾驶舱cognos地址，`系统管理` -> `报表管理` -> `驾驶舱菜单维护`

##系统停服通知 (★★★☆☆)

- [ ] 增加关闭系统通知，关闭系统后访问系统显示`系统暂停服务`提示页面

##cognos迁移到新服务器 (★★★☆☆)

cognos服务器迁移时需要做的工作

- [ ] ReportDispatch需要部署到was上(注意修改配置文件)
- [ ] `shellShedule.jar` 和 `shellShedule.sh` 需要放到10,11,12其中一台服务器上，webservice链接改成200服务器。

## 日常运维 (★★☆☆☆)

日常要处理的工作

- [ ] 指标导出
参考sql文件[导出指标信息](/atts/sql/导出指标信息.txt)和合并User的java脚本[XlsReader.java](/atts/java/XlsReader.java)

- [ ] cognos报表上传到oa资源库
可以在`系统监控` -> `监控设置` -> `报表调度数据维护` 进行维护
__调度流程参考[离线调度](2015-08-03-report-dispatch.md)__

## 了解websphere集群 (★★☆☆☆)
- [ ] 新生产环境为websphere集群，需要了解__集群配置和基本部署操作参考[portal集群安装](2015-08-05-was-cluster.md)__

---
#其他
- 日志文件有点大可以将最新的日志截取另存为新文件  
`tail -200000 catalina.out > catalina.tail200000.out`






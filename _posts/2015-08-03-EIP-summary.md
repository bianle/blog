# portal bug修改
## portal 在本地测试机上springcurity报异常
解决：
修改`web.xml`文件,将`classpath:`改为`/WEB-INF/classes/`
```
<context-param>
	  <param-name>contextConfigLocation</param-name>
	  <param-value>/WEB-INF/classes/applicationContext*.xml</param-value>
 </context-param>
```
# portal 优化
## 将portal源代码加入svn库
原portal源代码版本管理混乱不完整，将整理后的portal源码加入svn，新分支[portal_20150305](https://10.137.80.91:6103/svn/root/EIP/EIP2/dwcode/portal-java/dataapp_20150305)
## 菜单查询逻辑优化
## 其他SQL修改
---
layout: post
title: EIP总结
category: work
comments: false
---


# portal 需求变更

## 首页
+ 欢迎页改成默认模块
+ 首页没权限的模块不显示（原来置灰）
+ 顶部菜单只显示一级

## 标准化管理模块
+ 新增指标改成不需要申请单也可以新增
+ 新增维度做成不选择申请单可以新增
+ 指标信息加入KPI分类
+ 流程申请中返回按钮回到历史页面
+ 申请单附件丢失
+ 代办申请单默认状态禁用改为启用

## OA指标字典
+ 指标字典改为和portal一套程序，同数据库  
**注意：portal中针对oa指标字典新增加了一个`indicatorListPub.action`此页面对外开放，为了保证oa指标字典链接不变，更新时需要将`indicatorListPub.action` 改为`indicatorList.action`**
如下:
`applicationContext-security-basic.xml`片段：
```
<!-- 指标字典开放 -->
 <s:intercept-url pattern="/system/indicator/indicatorList.action"  filters="none" /> 
 <s:intercept-url pattern="/system/indicator/getIndicatorListPub.action"  filters="none" /> 
 <s:intercept-url pattern="/system/indicator/getHistoryByIdPub.action"  filters="none" />
```
`struts-standardization.xml`片段：
```
<action name="indicatorList" class="indicatorAction" method="indicatorList">
	  <result name="success">/pages/standardization/indicator/list1.jsp</result>
 </action>
```
+ 标准化指标字典隐藏已禁用指标

## cognos 集成
+ 增加url查询参数
+ cognos登录改为form表单模式


## 数据管理模块
+ 数据导入附件上传，数据存到180.1
+ 修改数据导入排序

## 增加内容发布模块
+ 客户经营管理报表
+ 历史报表




# portal 日常工作
## 指标导出
整理有sql文件和合并User的java脚本

##cognos报表上传到oa资源库
可以在`系统监控` -> `监控设置` -> `报表调度数据维护` 进行维护

## 了解websphere集群
新生产环境为websphere集群，需要了解集群基本部署操作

## cognos集成
+ cognos登录存在跨域安全性问题
+ 修改cognos服务器地址 ，`系统管理` -> `门户维护` -> `报表维护` -> `报表服务器地址维护` -> 启用
+ 修改领导驾驶舱cognos地址，`系统管理` -> `报表管理` -> `驾驶舱菜单维护` 




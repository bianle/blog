---
layout: post
title: cognos收集
category: BI
comments: false
---

## 前言

由于从Cognos 10.2版本开始不再支持NTLM方式的认证，所以我们需要一种简单的方式来配置权限认证，根据Cognos SDK自己带的例子，我做了一些修改，使其支持sqlserver、db2、oracle三种数据库的Java Provider方式的权限认证。

## 驱动

测试数据库的版本分别为Sqlserver 2005，db2 V9.7，oracle 10g，Sqlserver、db2和oracle所对应的的驱动分别为`sqljdbc4.jar`、`db2jcc.jar`、`ojdbc14.jar`，请根据实际数据库的版本下载相应的数据库驱动。根据数据库的类型选择相应驱动，并将驱动拷贝到`Cognos_Home\webapps\p2pd\WEB-INF\lib`下面。

## 配置文件

将数据库配置文件`JDBC_Config_Sample1.properties`拷贝到`Cognos_Home\configuration`下面，并根据所选数据库进行相应的修改。

* Sqlserver

```
#For Microsoft SQL Server connections
connectionString=jdbc:JSQLConnect://<i>localhost:1433</i>/sdk/applicationName=IBM_C8_CM
driverClass=com.jnetdirect.jsql.JSQLDriver
username=sa
password=apple
singleSignon=false
```

根据实际数据库的IP、端口号、数据库标识、用户名和密码进行修改。

* DB2

```
# For DB2 connections
connectionString=jdbc:db2://localhost:50000/COGNOS:retrieveMessagesFromServerOnGetMessage=true;fullyMaterializeLobData=false;
driverClass=com.ibm.db2.jcc.DB2Driver
username=cognos
password=cognos
singleSignon=false
```
根据实际数据库的IP、端口号、数据库标识、用户名和密码进行修改。

* oracle

```
# For oracle connections
connectionString=jdbc:oracle:thin:@localhost:1521:cognos
driverClass=oracle.jdbc.driver.OracleDriver
username=cognos
password=cognos
singleSignon=false
```
根据实际数据库的IP、端口号、数据库标识、用户名和密码进行修改。

## Java Provider Jar文件

将CAM_AAA_JDBCSample.jar拷贝到Cognos_Home\webapps\p2pd\WEB-INF\lib下面。

## 创建表

使用脚本dbInit_sqlserver.sql创建sqlserver数据库的表和视图；
使用脚本dbInit_db2.sql创建db2数据库的表和视图；
使用脚本dbInit_oracle.sql创建oracle数据库的表和视图。

## 插入数据

在用户表users和分组表groups中分别插入用户和分组数据。
对于users表：字段USERID,USERNAME,PASSWORD,LOCALE,TENANT为必填项，其中locale根据需要填写相应值，中文为zh-cn，英文为en；因为不考虑多租户的情况，所以对于所有用户的tenant赋予一个统一值，并且users和groups两个表中的tenant的值要一样，对于字段FULLNAME,EMAIL可以为空。
对于groups表：字段GROUPID,GROUPNAME,USERID,TENANT都不能为空，tenant的值要与users表中的tenant值一致。

## 配置cognos configuration

1. 打开cognos configuration，选中“身份验证”点击右键选择“新建资源->名称空间”
 
2. 类型选择“自定义java程序”。在“名称空间标识符”处填写“Sample1”，注意此处必须填写Sampe1，这个名字与配置文件JDBC_Config_Sample1.properties名字中的Sample1是对应的。在“Java类名称”处填写“JDBCSample”，将是否允许匿名访问设置为否。
3. 保存配置，重启cognos 。
4. 登陆cognos connection ，即可看到设置的名称空间Sample1



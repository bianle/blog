---
layout: post
title: oracle SQL 收集
category: db
comments: false
---

### 主键自增

```sql
/*1.新建索引*/
CREATE SEQUENCE SEQ_FOR_TB1
INCREMENT BY 1 /*-– 每次加几个*/
START WITH 1 /*-– 从1开始计数*/
NOMAXVALUE /*-– 不设置最大值*/
NOCYCLE /*-– 一直累加，不循环*/
CACHE 10;
/*2.增加触发器*/
CREATE TRIGGER TB1_AUTO_PK BEFORE
insert ON t_tb FOR EACH ROW
begin
select SEQ_FOR_TB1.nextval into:New.ID from dual;
end;

```

### 正则表达式

```sql
SELECT REGEXP_REPLACE('[1,2,3]99990.0[%]','\[[^\[]*\]','') FROM dual; 
```

### 分析函数

* SYS_CONNECT_BY_PATH

分组求和，文字类型以逗号拼接

<<<<<<< HEAD
  ND   XL  BZ
1 2012 100 苹果
2 2013 200 苹果
3 2014 300 苹果
4 2012 200 香蕉
5 2013 300 香蕉
6 2014 400 香蕉
7 2014 600 橘子
=======
|  |ND   |XL  |BZ|
|--|-----|----|---|
|1 |2012 |100 |苹果|
|2 |2013 |200 |苹果|
|3 |2014 |300 |苹果|
|4 |2012 |200 |香蕉|
|5 |2013 |300 |香蕉|
|6 |2014 |400 |香蕉|
|7 |2014 |600 |橘子|
>>>>>>> ada366d4222f72e3789e1b59471fb12a866ddfd4

按年度分组求每年销量，及每年销售种类

```sql
SELECT ND,
       SUM(XL) SUM_XL,
       MAX(LTRIM(SYS_CONNECT_BY_PATH(BZ, ','), ',')) SUM_BZ
  FROM (SELECT ND, XL, BZ, ROW_NUMBER() OVER(PARTITION BY ND ORDER BY XL) RN
          FROM SMP)
 START WITH RN = 1
CONNECT BY PRIOR RN + 1 = RN
       AND PRIOR ND = ND
 GROUP BY ND;
```

* 累计

```sql
WITH T AS
 (SELECT 1370.80 CL, 1214.01 XL, 284 MJA, 276 MJB, 1 M
    FROM DUAL
  UNION ALL
  SELECT 922.63 CL, 970.01 XL, 235 MJA, 224 MJB, 2 M
    FROM DUAL
  UNION ALL
  SELECT 852.39 CL, 864.79 XL, 168 MJA, 168 MJB, 3 M
    FROM DUAL
  UNION ALL
  SELECT 1007.85 CL, 974.16 XL, 188 MJA, 182 MJB, 4 M
    FROM DUAL
  UNION ALL
  SELECT 1019.80 CL, 1073.05 XL, 176 MJA, 156 MJB, 5 M
    FROM DUAL
  UNION ALL
  SELECT 1309.98 CL, 1209.75 XL, 223 MJA, 200 MJB, 7 M
    FROM DUAL
  UNION ALL
  SELECT 1308.83 CL, 1567.57 XL, 234 MJA, 243 MJB, 8 M FROM DUAL)
SELECT T.M,
       T.CL,
       SUM(T.CL) OVER(ORDER BY ROWNUM ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) CLLJ,
       T.XL,
       SUM(T.XL) OVER(ORDER BY ROWNUM ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) XLLJ,
       T.MJA,
       T.MJB
  FROM T

```

### oracle job

```sql

--创建测试表
CREATE TABLE SMP (A DATE);
--创建测试存储过程
CREATE OR REPLACE PROCEDURE SMP_PROC AS
BEGIN
    INSERT INTO SMP VALUES (SYSDATE)
END;

--1.定义数字
DECLARE SMP_NUM NUMBER;

--2.定义job
BEGIN
    DBMS_JOB.SUBMIT(
        JOB=>SMP_JOB,
		WHAT=>'SMP_PROC'
		NEXT_DATE=>SYSDATE
		INTERVAL=>'TRUNC(SYSDATE + 1) + (7*60+30)/(24*60)',/*每天7点半*/
		NO_PARSE=>TRUE
    )
END;

--3.查询job
SELECT * FROM USER_JOBS;

--4.删除job
BEGIN
SBMS_JOB.REMOVE(150);
END;

--5.修改job
BEGIN
DBMS_JOB.BROKEN(287,FALSE,TRUNC(SYSDATE,'HH')+2/24);
END;

--6.启动job
BEGIN
DBMS_JOB.RUN(287);
END;

/*--------------------------------------------------*/

/*每个午夜12点*/
SELECT TRUNC(SYSDATE + 1) FROM DUAL;

/*每个早上8点半*/
SELECT TRUNC(SYSDATE + 1) + (8 * 60 + 30） / (24 * 60) FROM DUAL;

/*每个星期二中午12点*/
SELECT NEXT_DAY(TRUNC(SYSDATE), '星期二') + 12 / 24 FROM DUAL;

/*每月第一天的12点*/
SELECT TRUNC(LAST_DAY(SYSDATE) + 1) FROM DUAL;

/*每个季度最后一天的晚上11点*/
SELECT TRUNC(ADD_MONTHS(SYSDATE + 2 / 24, 3), 'Q') - 1 / 24 FROM DUAL;

/*每星期六和日早上6点10分*/
SELECT TRUNC(LEAST(NEXT_DAY(SYSDATE, '星期六'), NEXT_DAY(SYSDATE, '星期日'))) +
       （6 * 60 + 10） / (24 * 60)
  FROM DUAL;

/*每分钟*/
SELECT TRUNC(SYSDATE, 'mi') + 1 / (24 * 60), SYSDATE + 1 / 1440 FROM DUAL;

/*每天凌晨1点*/
SELECT TRUNC(SYSDATE) + 1 + 1 / (24) FROM DUAL;

/*每周一凌晨1点*/
SELECT TRUNC(NEXT_DAY(SYSDATE, '星期一')) + 1 / 24 FROM DUAL;

/*每月1日凌晨1点*/
SELECT TRUNC(LAST_DAY(SYSDATE)) + 1 + 1 / 24 FROM DUAL;

/*每季度第一天凌晨1点*/
SELECT TRUNC(ADD_MONTHS(SYSDATE, 3), 'Q') + 1 / 24 FROM DUAL;

/*每年7月1日凌晨1点*/
SELECT ADD_MONTHS(TRUNC(SYSDATE, 'yyyy'), 6) + 1 / 24 FROM DUAL;

/*每年1月1日凌晨1点*/
SELECT ADD_MONTHS(TRUNC(SYSDATE, 'yyyy'), 12) + 1 / 24 FROM DUAL;

/*--------------------------------------------------*/

```

### oracle 记录被锁住

* 单节点

```sql
SELECT OBJECT_ID, SESSION_ID, LOCKED_MODE FROM V$LOCKED_OBJECT;

SELECT T2.USERNAME, T2.SID, T2.SERIAL#, T2.LOGON_TIME
  FROM V$LOCKED_OBJECT T1, V$SESSION T2
 WHERE T1.SESSION_ID = T2.SID;

ALTER SYSTEM KILL SESSION '290,5393'; /* SID,SERIAL# */

--查询导致被锁的SQL语句
SELECT SQL_TEXT
  FROM V$SQL
 WHERE HASH_VALUE IN
       (SELECT SQL_HASH_VALUE
          FROM V$SESSION
         WHERE SID IN (SELECT SESSION_ID FROM V$LOCKED_OBJECT))

--批量杀session
SELECT SID,
       SERIAL#,
       USERNAME,
       'alter system kill session ''' || SID || ',' || SERIAL# || ''';'
  FROM V$SESSION
 WHERE STATUS = 'INACTIVE'
   AND USERNAME = 'GDSAFETY';

```

* 集群

```sql
/*查session（如果数据库集群gv$session）*/
select username,sid,serial# from gv$session WHERE username LIKE 'GDSAFETY'/*用户*/

/*杀session*/
alter system kill session'153,20765' 
/*gv$是全局视图，而v$是针对某个实例的视图，$X是所有gv$的数据来源，从gv$到v$需要加上where inst_id = USERENV(’Instance’)*/
SELECT S.INST_ID,
       S.SID,
       S.SERIAL#,
       P.SPID, /*线程号*/
       S.USERNAME,
       S.PROGRAM,
       P.PID
  FROM GV$SESSION S
  JOIN GV$PROCESS P
    ON P.ADDR = S.PADDR
   AND P.INST_ID = S.INST_ID
 WHERE S.TYPE != 'BACKGROUND'
   AND S.USERNAME = 'GDSAFETY'
```

* 锁住时间过长而杀不死的

```sql
/*OS级杀线程linux*/
kill -9 12345 /*spid*/
/*OS级杀线程windows*/
orakill orcl/*实例名*/ 12345/*spid*/
```

### 删除用户

```sql
alter user XXX account lock;
SELECT * FROM V$SESSION WHERE USERNAME='XXX';
alter system kill session '147,3749';
drop user XXX CASCADE;
```

### 修改用户密码

```sql
sqlplus / as sysdba ;
alter user system/*用户*/ identified by abc/*新密码*/; 
```

### 有趣的面试题

```sql

-- 小小+霸霸+王王=小霸王
--小=? 霸=? 王=?
--1,9,8
--xx+yy+zz=xyz
--x=?,y=?,z=?

--我的答案
WITH T AS
 (SELECT ROWNUM - 1 RN FROM DUAL CONNECT BY ROWNUM <= 10)
SELECT T1.RN A, T2.RN B, T3.RN C
  FROM T T1, T T2, T T3
 WHERE TO_NUMBER(T1.RN || T1.RN) + TO_NUMBER(T2.RN || T2.RN) +
       TO_NUMBER(T3.RN || T3.RN) = TO_NUMBER(T1.RN || T2.RN || T3.RN);

--官方的
WITH N_LIST AS
 (SELECT ROWNUM N FROM DUAL CONNECT BY ROWNUM < 1000)
SELECT N
  FROM N_LIST
 WHERE (SUBSTR(N, 1, 1) + SUBSTR(N, 2, 1) + SUBSTR(N, 3, 1)) * 11 = N
   AND N > 100;



```

### sql 执行时间长

```sql
SELECT * FROM v$session WHERE username = 'XXX';
SELECT * FROM v$sql WHERE sql_id = 'crkxpjsfju45z'
SELECT * FROM v$transaction;
SELECT NAME, VALUE FROM gv$parameter WHERE NAME = 'resource_limit';


--查看resource limit是否开启
 SELECT NAME, VALUE FROM gv$parameter WHERE NAME = 'resource_limit';
 --开启resource limit
 ALTER system SET resource_limit = TRUE;
 
--查询资源文件，找到CONNECT_TIME所在的profile名。
 SELECT resource_name, profile FROM dba_profiles;
 --用alter命令修改profile中的值；--（或跟一个时间值，如1000，单位为分钟）
 ALTER profile monitoring_profile LIMIT connect_time unlimited;
 ALTER profile monitoring_profile LIMIT idle_time unlimited;
 
--用如下命令查看profile中的值；
 SELECT resource_name, LIMIT
   FROM dba_profiles
 WHERE profile = 'MONITORING_PROFILE';

```

### oracle 查看状态

* 查看表空间使用情况

```sql
SELECT UPPER(F.TABLESPACE_NAME) "表空间名",
　　D.TOT_GROOTTE_MB "表空间大小(M)",
　　D.TOT_GROOTTE_MB - F.TOTAL_BYTES "已使用空间(M)",
　　TO_CHAR(ROUND((D.TOT_GROOTTE_MB - F.TOTAL_BYTES) / D.TOT_GROOTTE_MB * 100,2),'990.99') || '%' "使用比",
　　F.TOTAL_BYTES "空闲空间(M)",
　　F.MAX_BYTES "最大块(M)"
　　FROM (SELECT TABLESPACE_NAME,
　　ROUND(SUM(BYTES) / (1024 * 1024), 2) TOTAL_BYTES,
　　ROUND(MAX(BYTES) / (1024 * 1024), 2) MAX_BYTES
　　FROM SYS.DBA_FREE_SPACE
　　GROUP BY TABLESPACE_NAME) F,
　　(SELECT DD.TABLESPACE_NAME,
　　 ROUND(SUM(DD.BYTES) / (1024 * 1024), 2) TOT_GROOTTE_MB
　　FROM SYS.DBA_DATA_FILES DD
　　GROUP BY DD.TABLESPACE_NAME) D
　　WHERE D.TABLESPACE_NAME = F.TABLESPACE_NAME
　　ORDER BY 1;
```

* 查看表空间可用容量

```sql
select tablespace_name,
　　count(*) as extends,
　　round(sum(bytes) / 1024 / 1024, 2) as MB,
　　sum(blocks) as blocks
　　from dba_free_space
　　group by tablespace_name;
```

* 查看正在执行的SQL和执行该SQL的用户

```sql
--查询Oracle正在执行的sql语句及执行该语句的用户
SELECT b.sid oracleID,
       b.username 登录Oracle用户名,
       b.serial#,
       spid 操作系统ID,
       paddr,
       sql_text 正在执行的SQL,
       b.machine 计算机名
FROM v$process a, v$session b, v$sqlarea c
WHERE a.addr = b.paddr
   AND b.sql_hash_value = c.hash_value
   AND b.username = 'GDSAFETY'
   
--查看正在执行sql的发起者和发起程序
SELECT OSUSER 电脑登录身份,
       PROGRAM 发起请求的程序,
       USERNAME 登录系统的用户名,
       SCHEMANAME,
       B.Cpu_Time 花费cpu的时间,
       STATUS,
       B.SQL_TEXT 执行的sql
FROM V$SESSION A
LEFT JOIN V$SQL B ON A.SQL_ADDRESS = B.ADDRESS
                   AND A.SQL_HASH_VALUE = B.HASH_VALUE
ORDER BY b.cpu_time DESC

--查出oracle当前的被锁对象
SELECT l.session_id sid,
       s.serial#,
       l.locked_mode 锁模式,
       l.oracle_username 登录用户,
       l.os_user_name 登录机器用户名,
       s.machine 机器名,
       s.terminal 终端用户名,
       o.object_name 被锁对象名,
       s.logon_time 登录数据库时间
FROM v$locked_object l, all_objects o, v$session s
WHERE l.object_id = o.object_id
   AND l.session_id = s.sid
ORDER BY sid, s.serial#;

```

* 查看表结构

```sql
SELECT T.OBJECT_NAME,
       C.COLUMN_NAME,
       C.DATA_TYPE,
       C.DATA_LENGTH,
       C.DATA_PRECISION,
       C.DATA_SCALE
  FROM USER_OBJECTS T, USER_TAB_COLUMNS C
 WHERE T.OBJECT_TYPE = 'TABLE'
   AND C.TABLE_NAME = T.OBJECT_NAME
 ORDER BY OBJECT_NAME, COLUMN_NAME;
```


* 查看表大小

```sql
Select Segment_Name,Sum(bytes)/1024/1024 sz From User_Extents Group By Segment_Name ORDER BY sz DESC;

```

* 查询插入记录的时间

```sql
SELECT t.*,ORA_ROWSCN FROM sms_log t;

select to_char(scn_to_timestamp(10896271687717),'yyyy-mm-dd hh24:mi:ss') insert_time from dual;

```

### oracle 错误解决办法

* ORA-01658 : 无法为表空间space中的段创建 INITIAL 区；

```sql
SELECT SEGMENT_TYPE, OWNER, SUM(BYTES) / 1024 / 1024
  FROM DBA_SEGMENTS T
 WHERE TABLESPACE_NAME = 'USERS'
 GROUP BY SEGMENT_TYPE, OWNER;

SELECT *
  FROM DBA_TABLESPACES --查看表空间
       --查看表空间文件路径
         SELECT TABLESPACE_NAME,
                FILE_ID,
                BYTES / 1024 / 1024,
                FILE_NAME 　　
           FROM DBA_DATA_FILES
          ORDER BY FILE_ID;


--查看用户和默认表空间的关系
SELECT USERNAME, DEFAULT_TABLESPACE FROM DBA_USERS;

--alter tablespace USERS add datafile 'D:\SOFTWARE\ORACLE\PRODUCT\10.2.0\ORADATA\ORCL\USERS01.DBF' size 10m; 
ALTER TABLESPACE USERS ADD DATAFILE 'D:\SOFTWARE\ORACLE\PRODUCT\10.2.0\ORADATA\ORCL\USERS01.DBF' SIZE 1024M AUTOEXTEND ON NEXT 50M MAXSIZE UNLIMITED;

```

### oracle 创建表空间

```sql
/*分为四步 */
/*第1步：创建临时表空间  */
create temporary tablespace user_temp  
tempfile 'D:\oracle\oradata\Oracle9i\user_temp.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m  
extent management local;  
 
/*第2步：创建数据表空间  */
create tablespace gdsafety  
logging  
datafile 'D:\software\oracle\product\10.2.0\oradata\tablespaces\gdsafety.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m  
extent management local;  
 
/*第3步：创建用户并指定表空间  */
create user username identified by password  
default tablespace user_data  
temporary tablespace user_temp;  
 
/*第4步：给用户授予权限  */
grant connect,resource,dba to username;
/*修改默认表空间*/
ALTER USER gdsafety default tablespace gdsafety ;  

```


### oracle 数据恢复

* 闪回

```sql
select * from d_pub_reports as of timestamp to_timestamp('2014-01-15 22:50:00','yyyy-mm-dd hh24:mi:ss'); 
--恢复到某个时间点
flashback table d_pub_reports to timestamp to_timestamp('2014-01-15 22:50:00','yyyy-mm-dd hh24:mi:ss');
--恢复到删除前
flashback table t_item_system to before drop;
```

* 恢复视图

```sql
SELECT *
  FROM ALL_SOURCE AS OF TIMESTAMP(SYSTIMESTAMP - INTERVAL '6000' SECOND)
 WHERE TYPE = 'PROCEDURE'
   AND OWNER = 'GDSAFETY'
   AND NAME = 'PRO_PRODUCTION_SBDJHSJ_M'
 ORDER BY LINE

```

### oracle 禁用启用外键

删除表数据时，外键约束，先禁用外键约束，删除后启用约束

```sql

/*查询语句*/
SELECT * FROM d_pub_org;
SELECT * FROM d_pub_org@DB237;

/*查询表的主键*/
SELECT *
  FROM DBA_CONSTRAINTS C
 WHERE 1 = 1
   AND C.OWNER = 'GDSAFETY'
   AND C.TABLE_NAME = 'D_PUB_ORG'
   AND C.CONSTRAINT_TYPE = 'P';
AND c.constraint_name LIKE 'FK_T_ORG_%'
/*根据表的主键找外键*/
SELECT *
  FROM DBA_CONSTRAINTS C
 WHERE 1 = 1
   AND C.R_CONSTRAINT_NAME = 'PK_D_PUB_ORG'
/*禁用外键*/
alter table T_ORG_BASISMESSAGE/*表名*/ disable constraint FK_T_ORG_BA_REFERENCE_D_PUB_OR/*约束名*/;
alter table T_SAFETY_ATTACH_OTHER/*表名*/ disable constraint FK_T_SAFETY_REFERENCE_D_PUB_OR/*约束名*/;
/*删除数据*/
DELETE FROM d_pub_org;
/*导入数据*/
INSERT INTO d_pub_org SELECT * FROM d_pub_org@DB237;
/*启用约束*/
alter table T_ORG_BASISMESSAGE/*表名*/ ENABLE constraint FK_T_ORG_BA_REFERENCE_D_PUB_OR/*约束名*/ ;
alter table T_SAFETY_ATTACH_OTHER/*表名*/ ENABLE constraint FK_T_SAFETY_REFERENCE_D_PUB_OR/*约束名*/;

/*根据表名找子表*/
SELECT TABLE_NAME
  FROM DBA_CONSTRAINTS C
 WHERE 1 = 1
   AND C.R_CONSTRAINT_NAME =
       (SELECT CONSTRAINT_NAME
          FROM DBA_CONSTRAINTS C
         WHERE 1 = 1
           AND C.TABLE_NAME = 'T_SCIENCE_PLAN_GATHER'
           AND C.CONSTRAINT_TYPE = 'P')


```

### oracle 同步表和视图

```sql

--1.普通表
drop table T_YX_FWGDL_CHECK; /*扔掉表*/
create table T_PRO_UNIT_STATUSBB as select * from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;/*同步表*/
--2.视图
select 'create view ' || view_name || ' as', text
  from dba_views@gdsafetyah_237
 where view_name = 'V_PUB_CAPACITY_VALUE'
   and owner = 'GDSAFETYAH';
--3.带long类型的表的同步

Declare
CURSOR bcur
 IS select TIME_ID,BYCONTEN1 from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;
  brec bcur%ROWTYPE;
 BEGIN
 insert into T_PRO_UNIT_STATUSBB(TIME_ID,content,Byconten) select TIME_ID,content,Byconten from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;/*其它类型先插入*/
 OPEN bcur;
 LOOP
 FETCH bcur INTO brec;
 EXIT WHEN bcur%NOTFOUND;
 update T_PRO_UNIT_STATUSBB set BYCONTEN1=brec.BYCONTEN1 where TIME_ID=brec.TIME_ID;/*根据id更新long字段*/
 END LOOP;
 CLOSE bcur;
 END;

```

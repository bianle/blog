---
layout: post
title: oracle SQL �ռ�
category: db
comments: false
---

### ��������

```sql
/*1.�½�����*/
CREATE SEQUENCE SEQ_FOR_TB1
INCREMENT BY 1 /*-�C ÿ�μӼ���*/
START WITH 1 /*-�C ��1��ʼ����*/
NOMAXVALUE /*-�C ���������ֵ*/
NOCYCLE /*-�C һֱ�ۼӣ���ѭ��*/
CACHE 10;
/*2.���Ӵ�����*/
CREATE TRIGGER TB1_AUTO_PK BEFORE
insert ON t_tb FOR EACH ROW
begin
select SEQ_FOR_TB1.nextval into:New.ID from dual;
end;

```

### ������ʽ

```sql
SELECT REGEXP_REPLACE('[1,2,3]99990.0[%]','\[[^\[]*\]','') FROM dual; 
```

### ��������

* SYS_CONNECT_BY_PATH

������ͣ����������Զ���ƴ��

  ND   XL  BZ
1 2012 100 ƻ��
2 2013 200 ƻ��
3 2014 300 ƻ��
4 2012 200 �㽶
5 2013 300 �㽶
6 2014 400 �㽶
7 2014 600 ����

����ȷ�����ÿ����������ÿ����������

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

* �ۼ�

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

--�������Ա�
CREATE TABLE SMP (A DATE);
--�������Դ洢����
CREATE OR REPLACE PROCEDURE SMP_PROC AS
BEGIN
    INSERT INTO SMP VALUES (SYSDATE)
END;

--1.��������
DECLARE SMP_NUM NUMBER;

--2.����job
BEGIN
    DBMS_JOB.SUBMIT(
        JOB=>SMP_JOB,
		WHAT=>'SMP_PROC'
		NEXT_DATE=>SYSDATE
		INTERVAL=>'TRUNC(SYSDATE + 1) + (7*60+30)/(24*60)',/*ÿ��7���*/
		NO_PARSE=>TRUE
    )
END;

--3.��ѯjob
SELECT * FROM USER_JOBS;

--4.ɾ��job
BEGIN
SBMS_JOB.REMOVE(150);
END;

--5.�޸�job
BEGIN
DBMS_JOB.BROKEN(287,FALSE,TRUNC(SYSDATE,'HH')+2/24);
END;

--6.����job
BEGIN
DBMS_JOB.RUN(287);
END;

/*--------------------------------------------------*/

/*ÿ����ҹ12��*/
SELECT TRUNC(SYSDATE + 1) FROM DUAL;

/*ÿ������8���*/
SELECT TRUNC(SYSDATE + 1) + (8 * 60 + 30�� / (24 * 60) FROM DUAL;

/*ÿ�����ڶ�����12��*/
SELECT NEXT_DAY(TRUNC(SYSDATE), '���ڶ�') + 12 / 24 FROM DUAL;

/*ÿ�µ�һ���12��*/
SELECT TRUNC(LAST_DAY(SYSDATE) + 1) FROM DUAL;

/*ÿ���������һ�������11��*/
SELECT TRUNC(ADD_MONTHS(SYSDATE + 2 / 24, 3), 'Q') - 1 / 24 FROM DUAL;

/*ÿ��������������6��10��*/
SELECT TRUNC(LEAST(NEXT_DAY(SYSDATE, '������'), NEXT_DAY(SYSDATE, '������'))) +
       ��6 * 60 + 10�� / (24 * 60)
  FROM DUAL;

/*ÿ����*/
SELECT TRUNC(SYSDATE, 'mi') + 1 / (24 * 60), SYSDATE + 1 / 1440 FROM DUAL;

/*ÿ���賿1��*/
SELECT TRUNC(SYSDATE) + 1 + 1 / (24) FROM DUAL;

/*ÿ��һ�賿1��*/
SELECT TRUNC(NEXT_DAY(SYSDATE, '����һ')) + 1 / 24 FROM DUAL;

/*ÿ��1���賿1��*/
SELECT TRUNC(LAST_DAY(SYSDATE)) + 1 + 1 / 24 FROM DUAL;

/*ÿ���ȵ�һ���賿1��*/
SELECT TRUNC(ADD_MONTHS(SYSDATE, 3), 'Q') + 1 / 24 FROM DUAL;

/*ÿ��7��1���賿1��*/
SELECT ADD_MONTHS(TRUNC(SYSDATE, 'yyyy'), 6) + 1 / 24 FROM DUAL;

/*ÿ��1��1���賿1��*/
SELECT ADD_MONTHS(TRUNC(SYSDATE, 'yyyy'), 12) + 1 / 24 FROM DUAL;

/*--------------------------------------------------*/

```

### oracle ��¼����ס

* ���ڵ�

```sql
SELECT OBJECT_ID, SESSION_ID, LOCKED_MODE FROM V$LOCKED_OBJECT;

SELECT T2.USERNAME, T2.SID, T2.SERIAL#, T2.LOGON_TIME
  FROM V$LOCKED_OBJECT T1, V$SESSION T2
 WHERE T1.SESSION_ID = T2.SID;

ALTER SYSTEM KILL SESSION '290,5393'; /* SID,SERIAL# */

--��ѯ���±�����SQL���
SELECT SQL_TEXT
  FROM V$SQL
 WHERE HASH_VALUE IN
       (SELECT SQL_HASH_VALUE
          FROM V$SESSION
         WHERE SID IN (SELECT SESSION_ID FROM V$LOCKED_OBJECT))

--����ɱsession
SELECT SID,
       SERIAL#,
       USERNAME,
       'alter system kill session ''' || SID || ',' || SERIAL# || ''';'
  FROM V$SESSION
 WHERE STATUS = 'INACTIVE'
   AND USERNAME = 'GDSAFETY';

```

* ��Ⱥ

```sql
/*��session��������ݿ⼯Ⱥgv$session��*/
select username,sid,serial# from gv$session WHERE username LIKE 'GDSAFETY'/*�û�*/

/*ɱsession*/
alter system kill session'153,20765' 
/*gv$��ȫ����ͼ����v$�����ĳ��ʵ������ͼ��$X������gv$��������Դ����gv$��v$��Ҫ����where inst_id = USERENV(��Instance��)*/
SELECT S.INST_ID,
       S.SID,
       S.SERIAL#,
       P.SPID, /*�̺߳�*/
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

* ��סʱ�������ɱ������

```sql
/*OS��ɱ�߳�linux*/
kill -9 12345 /*spid*/
/*OS��ɱ�߳�windows*/
orakill orcl/*ʵ����*/ 12345/*spid*/
```

### ɾ���û�

```sql
alter user XXX account lock;
SELECT * FROM V$SESSION WHERE USERNAME='XXX';
alter system kill session '147,3749';
drop user XXX CASCADE;
```

### �޸��û�����

```sql
sqlplus / as sysdba ;
alter user system/*�û�*/ identified by abc/*������*/; 
```

### ��Ȥ��������

```sql

-- СС+�԰�+����=С����
--С=? ��=? ��=?
--1,9,8
--xx+yy+zz=xyz
--x=?,y=?,z=?

--�ҵĴ�
WITH T AS
 (SELECT ROWNUM - 1 RN FROM DUAL CONNECT BY ROWNUM <= 10)
SELECT T1.RN A, T2.RN B, T3.RN C
  FROM T T1, T T2, T T3
 WHERE TO_NUMBER(T1.RN || T1.RN) + TO_NUMBER(T2.RN || T2.RN) +
       TO_NUMBER(T3.RN || T3.RN) = TO_NUMBER(T1.RN || T2.RN || T3.RN);

--�ٷ���
WITH N_LIST AS
 (SELECT ROWNUM N FROM DUAL CONNECT BY ROWNUM < 1000)
SELECT N
  FROM N_LIST
 WHERE (SUBSTR(N, 1, 1) + SUBSTR(N, 2, 1) + SUBSTR(N, 3, 1)) * 11 = N
   AND N > 100;



```

### sql ִ��ʱ�䳤

```sql
SELECT * FROM v$session WHERE username = 'XXX';
SELECT * FROM v$sql WHERE sql_id = 'crkxpjsfju45z'
SELECT * FROM v$transaction;
SELECT NAME, VALUE FROM gv$parameter WHERE NAME = 'resource_limit';


--�鿴resource limit�Ƿ���
 SELECT NAME, VALUE FROM gv$parameter WHERE NAME = 'resource_limit';
 --����resource limit
 ALTER system SET resource_limit = TRUE;
 
--��ѯ��Դ�ļ����ҵ�CONNECT_TIME���ڵ�profile����
 SELECT resource_name, profile FROM dba_profiles;
 --��alter�����޸�profile�е�ֵ��--�����һ��ʱ��ֵ����1000����λΪ���ӣ�
 ALTER profile monitoring_profile LIMIT connect_time unlimited;
 ALTER profile monitoring_profile LIMIT idle_time unlimited;
 
--����������鿴profile�е�ֵ��
 SELECT resource_name, LIMIT
   FROM dba_profiles
 WHERE profile = 'MONITORING_PROFILE';

```

### oracle �鿴״̬

* �鿴��ռ�ʹ�����

```sql
SELECT UPPER(F.TABLESPACE_NAME) "��ռ���",
����D.TOT_GROOTTE_MB "��ռ��С(M)",
����D.TOT_GROOTTE_MB - F.TOTAL_BYTES "��ʹ�ÿռ�(M)",
����TO_CHAR(ROUND((D.TOT_GROOTTE_MB - F.TOTAL_BYTES) / D.TOT_GROOTTE_MB * 100,2),'990.99') || '%' "ʹ�ñ�",
����F.TOTAL_BYTES "���пռ�(M)",
����F.MAX_BYTES "����(M)"
����FROM (SELECT TABLESPACE_NAME,
����ROUND(SUM(BYTES) / (1024 * 1024), 2) TOTAL_BYTES,
����ROUND(MAX(BYTES) / (1024 * 1024), 2) MAX_BYTES
����FROM SYS.DBA_FREE_SPACE
����GROUP BY TABLESPACE_NAME) F,
����(SELECT DD.TABLESPACE_NAME,
���� ROUND(SUM(DD.BYTES) / (1024 * 1024), 2) TOT_GROOTTE_MB
����FROM SYS.DBA_DATA_FILES DD
����GROUP BY DD.TABLESPACE_NAME) D
����WHERE D.TABLESPACE_NAME = F.TABLESPACE_NAME
����ORDER BY 1;
```

* �鿴��ռ��������

```sql
select tablespace_name,
����count(*) as extends,
����round(sum(bytes) / 1024 / 1024, 2) as MB,
����sum(blocks) as blocks
����from dba_free_space
����group by tablespace_name;
```

* �鿴����ִ�е�SQL��ִ�и�SQL���û�

```sql
--��ѯOracle����ִ�е�sql��估ִ�и������û�
SELECT b.sid oracleID,
       b.username ��¼Oracle�û���,
       b.serial#,
       spid ����ϵͳID,
       paddr,
       sql_text ����ִ�е�SQL,
       b.machine �������
FROM v$process a, v$session b, v$sqlarea c
WHERE a.addr = b.paddr
   AND b.sql_hash_value = c.hash_value
   AND b.username = 'GDSAFETY'
   
--�鿴����ִ��sql�ķ����ߺͷ������
SELECT OSUSER ���Ե�¼���,
       PROGRAM ��������ĳ���,
       USERNAME ��¼ϵͳ���û���,
       SCHEMANAME,
       B.Cpu_Time ����cpu��ʱ��,
       STATUS,
       B.SQL_TEXT ִ�е�sql
FROM V$SESSION A
LEFT JOIN V$SQL B ON A.SQL_ADDRESS = B.ADDRESS
                   AND A.SQL_HASH_VALUE = B.HASH_VALUE
ORDER BY b.cpu_time DESC

--���oracle��ǰ�ı�������
SELECT l.session_id sid,
       s.serial#,
       l.locked_mode ��ģʽ,
       l.oracle_username ��¼�û�,
       l.os_user_name ��¼�����û���,
       s.machine ������,
       s.terminal �ն��û���,
       o.object_name ����������,
       s.logon_time ��¼���ݿ�ʱ��
FROM v$locked_object l, all_objects o, v$session s
WHERE l.object_id = o.object_id
   AND l.session_id = s.sid
ORDER BY sid, s.serial#;

```

* �鿴��ṹ

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


* �鿴���С

```sql
Select Segment_Name,Sum(bytes)/1024/1024 sz From User_Extents Group By Segment_Name ORDER BY sz DESC;

```

* ��ѯ�����¼��ʱ��

```sql
SELECT t.*,ORA_ROWSCN FROM sms_log t;

select to_char(scn_to_timestamp(10896271687717),'yyyy-mm-dd hh24:mi:ss') insert_time from dual;

```

### oracle �������취

* ORA-01658 : �޷�Ϊ��ռ�space�еĶδ��� INITIAL ����

```sql
SELECT SEGMENT_TYPE, OWNER, SUM(BYTES) / 1024 / 1024
  FROM DBA_SEGMENTS T
 WHERE TABLESPACE_NAME = 'USERS'
 GROUP BY SEGMENT_TYPE, OWNER;

SELECT *
  FROM DBA_TABLESPACES --�鿴��ռ�
       --�鿴��ռ��ļ�·��
         SELECT TABLESPACE_NAME,
                FILE_ID,
                BYTES / 1024 / 1024,
                FILE_NAME ����
           FROM DBA_DATA_FILES
          ORDER BY FILE_ID;


--�鿴�û���Ĭ�ϱ�ռ�Ĺ�ϵ
SELECT USERNAME, DEFAULT_TABLESPACE FROM DBA_USERS;

--alter tablespace USERS add datafile 'D:\SOFTWARE\ORACLE\PRODUCT\10.2.0\ORADATA\ORCL\USERS01.DBF' size 10m; 
ALTER TABLESPACE USERS ADD DATAFILE 'D:\SOFTWARE\ORACLE\PRODUCT\10.2.0\ORADATA\ORCL\USERS01.DBF' SIZE 1024M AUTOEXTEND ON NEXT 50M MAXSIZE UNLIMITED;

```

### oracle ������ռ�

```sql
/*��Ϊ�Ĳ� */
/*��1����������ʱ��ռ�  */
create temporary tablespace user_temp  
tempfile 'D:\oracle\oradata\Oracle9i\user_temp.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m  
extent management local;  
 
/*��2�����������ݱ�ռ�  */
create tablespace gdsafety  
logging  
datafile 'D:\software\oracle\product\10.2.0\oradata\tablespaces\gdsafety.dbf' 
size 50m  
autoextend on  
next 50m maxsize 20480m  
extent management local;  
 
/*��3���������û���ָ����ռ�  */
create user username identified by password  
default tablespace user_data  
temporary tablespace user_temp;  
 
/*��4�������û�����Ȩ��  */
grant connect,resource,dba to username;
/*�޸�Ĭ�ϱ�ռ�*/
ALTER USER gdsafety default tablespace gdsafety ;  

```


### oracle ���ݻָ�

* ����

```sql
select * from d_pub_reports as of timestamp to_timestamp('2014-01-15 22:50:00','yyyy-mm-dd hh24:mi:ss'); 
--�ָ���ĳ��ʱ���
flashback table d_pub_reports to timestamp to_timestamp('2014-01-15 22:50:00','yyyy-mm-dd hh24:mi:ss');
--�ָ���ɾ��ǰ
flashback table t_item_system to before drop;
```

* �ָ���ͼ

```sql
SELECT *
  FROM ALL_SOURCE AS OF TIMESTAMP(SYSTIMESTAMP - INTERVAL '6000' SECOND)
 WHERE TYPE = 'PROCEDURE'
   AND OWNER = 'GDSAFETY'
   AND NAME = 'PRO_PRODUCTION_SBDJHSJ_M'
 ORDER BY LINE

```

### oracle �����������

ɾ��������ʱ�����Լ�����Ƚ������Լ����ɾ��������Լ��

```sql

/*��ѯ���*/
SELECT * FROM d_pub_org;
SELECT * FROM d_pub_org@DB237;

/*��ѯ�������*/
SELECT *
  FROM DBA_CONSTRAINTS C
 WHERE 1 = 1
   AND C.OWNER = 'GDSAFETY'
   AND C.TABLE_NAME = 'D_PUB_ORG'
   AND C.CONSTRAINT_TYPE = 'P';
AND c.constraint_name LIKE 'FK_T_ORG_%'
/*���ݱ�����������*/
SELECT *
  FROM DBA_CONSTRAINTS C
 WHERE 1 = 1
   AND C.R_CONSTRAINT_NAME = 'PK_D_PUB_ORG'
/*�������*/
alter table T_ORG_BASISMESSAGE/*����*/ disable constraint FK_T_ORG_BA_REFERENCE_D_PUB_OR/*Լ����*/;
alter table T_SAFETY_ATTACH_OTHER/*����*/ disable constraint FK_T_SAFETY_REFERENCE_D_PUB_OR/*Լ����*/;
/*ɾ������*/
DELETE FROM d_pub_org;
/*��������*/
INSERT INTO d_pub_org SELECT * FROM d_pub_org@DB237;
/*����Լ��*/
alter table T_ORG_BASISMESSAGE/*����*/ ENABLE constraint FK_T_ORG_BA_REFERENCE_D_PUB_OR/*Լ����*/ ;
alter table T_SAFETY_ATTACH_OTHER/*����*/ ENABLE constraint FK_T_SAFETY_REFERENCE_D_PUB_OR/*Լ����*/;

/*���ݱ������ӱ�*/
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

### oracle ͬ�������ͼ

```sql

--1.��ͨ��
drop table T_YX_FWGDL_CHECK; /*�ӵ���*/
create table T_PRO_UNIT_STATUSBB as select * from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;/*ͬ����*/
--2.��ͼ
select 'create view ' || view_name || ' as', text
  from dba_views@gdsafetyah_237
 where view_name = 'V_PUB_CAPACITY_VALUE'
   and owner = 'GDSAFETYAH';
--3.��long���͵ı��ͬ��

Declare
CURSOR bcur
 IS select TIME_ID,BYCONTEN1 from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;
  brec bcur%ROWTYPE;
 BEGIN
 insert into T_PRO_UNIT_STATUSBB(TIME_ID,content,Byconten) select TIME_ID,content,Byconten from T_PRO_UNIT_STATUSBB@Gdsafetyah_237;/*���������Ȳ���*/
 OPEN bcur;
 LOOP
 FETCH bcur INTO brec;
 EXIT WHEN bcur%NOTFOUND;
 update T_PRO_UNIT_STATUSBB set BYCONTEN1=brec.BYCONTEN1 where TIME_ID=brec.TIME_ID;/*����id����long�ֶ�*/
 END LOOP;
 CLOSE bcur;
 END;

```

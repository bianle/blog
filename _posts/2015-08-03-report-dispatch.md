---
layout: post
title: EIP报表调度
category: work
comments: false
---

#调度流程

+ cube刷新完后向`T_DATA_SOURCE`表插入数据触发器`TR_DS_REPORT_UPDATE`被触发将`T_REPORT_DISPATCHINFO`表中刷新成功的报表状态(`EXECUTE_FLAG`)置为可执行(1)

`TR_DS_REPORT_UPDATE`

```
CREATE OR REPLACE TRIGGER "PORTAL"."TR_DS_REPORT_UPDATE"
AFTER INSERT ON T_DATA_SOURCE
REFERENCING NEW AS n
FOR EACH ROW MODE DB2SQL
WHEN (n.DS_STATUS='OK')
BEGIN ATOMIC

DECLARE v_sou_ds INTEGER;
DECLARE v_tar_ds INTEGER;
DECLARE v_current_day VARCHAR(15);

set v_current_day = (select current date from sysibm.sysdummy1);

for v_m as select report_id from T_DS_REPORT where ds_id=n.ds_id
do
set v_sou_ds = (select count(*) from T_DS_REPORT where report_id=v_m.report_id);
set v_tar_ds = (select count(*) from T_DATA_SOURCE where  substr(start_time,1,10) = v_current_day and ds_id in (select ds_id from T_DS_REPORT where report_id=v_m.report_id));

IF (v_sou_ds = v_tar_ds) THEN
update T_REPORT_DISPATCHINFO set EXECUTE_FLAG='1' where report_id = v_m.report_id;
END IF;

end for;

END
```

+所有cube刷新完毕后调度执行`rsh_shellSchedule.sh`

```
sh /informatic/PowerCenter8.6.1/server/infa_shared/shell_script/cognos/rsh_shellSchedule.sh
```
+ `rsh_shellSchedule.sh` 远程执行tomcat用户下shellSchedule命令

```
rsh tk-cognos -l tomcat /home/tomcat/shellSchedule/shellSchedule.sh
```
+ `shellSchedule.sh`运行`shellSchedule.jar`jar包并在当前目录写下日志`shellSchedule.log`

```
/usr/java6/bin/java -jar /home/tomcat/shellSchedule/shellSchedule.jar >> /home/tomcat/shellSchedule/shellSchedule.log
```

+ `shellSchedule.jar`调用`ReportDispatch`发布的webservice接口`http://10.129.80.4:8098/ReportDispatch/services/reportUpdateService`中的`shellUpdateReportInfo`服务

+ `shellUpdateReportInfo`查询`T_REPORT_DISPATCHINFO`表查询执行状态为可执行的(EXECUTE_FLAG=1)导出报表并上传到OA服务器



#其他
+ 日志文件有点大可以将最新的日志截取另存为新文件
`tail -200000 catalina.out > catalina.tail200000.out`

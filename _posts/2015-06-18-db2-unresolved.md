---
layout: post
title: db2 未解决问题
category: db
comments: false
---

### union

在项目中遇到排序字段左补齐

```sql
SELECT REPEAT('0',5-LENGTH(SORT||''))||SORT FROM T_SYS_FUNCTION_PRIVI T
```

其中`SORT`为integer类型，这个sql没问题。但当两表`UNION`时：

```sql
SELECT REPEAT('0',5-LENGTH(SORT||''))||SORT FROM T_SYS_FUNCTION_PRIVI T
UNION
SELECT REPEAT('0',5-LENGTH(SORT||''))||SORT FROM T_SYS_FUNCTION_PRIVI T
```
报错如下：

>[Error Code: -134, SQL State: 42907]  Improper use of a string column, host variable, constant, or function "Column 1".. SQLCODE=-134, SQLSTATE=42907, DRIVER=3.57.82. 2) [Error Code: -727, SQL State: 56098]  An error occurred during implicit system action type "2". Information returned for the error includes SQLCODE "-134", SQLSTATE "42907" and message tokens "Column 1".. SQLCODE=-727, SQLSTATE=56098, DRIVER=3.57.82
>... 1 statement(s) executed, 0 row(s) affected, exec/fetch time: 0.000/0.000 sec  [0 successful, 0 warnings, 1 errors]

改成如下：

```sql
SELECT RIGHT('00000'||SORT,5) FROM T_SYS_FUNCTION_PRIVI T
UNION
SELECT RIGHT('00000'||SORT,5) FROM T_SYS_FUNCTION_PRIVI T
```

则正常执行，不解！

---
layout: post
title: python 收集
category: python
comments: false
---

## Python SQLite

```
\#!/usr/bin/env python
\# -*- coding: utf-8 -*-

\#导入日志及SQLite3模块
import logging
import logging.config
import sqlite3

\#日志配置文件名
LOG_FILENAME = 'logging.conf'

\#日志语句提示信息
LOG_CONTENT_NAME = 'sqlite_log'

\#SQLite数据库名称
DB_SQLITE_PATH = ".\db\sqlite_pytest.db"

def log_init(log_config_filename, logname):
    '''
    Function:日志模块初始化函数
    Input：log_config_filename:日志配置文件名
           lognmae:每条日志前的提示语句
    Output: logger
    author: socrates
    date:2012-02-11
    '''
    logging.config.fileConfig(log_config_filename)
    logger = logging.getLogger(logname)
    return logger

def operate_sqlite3_tbl_product():
    '''
    Function:操作SQLITE3数据库函数
    Input：NONE
    Output: NONE
    author: socrates
    date:2012-02-11
    '''
    sqlite_logger.debug("operate_sqlite3_tbl_product enter...")
   
    #连接数据库 
    try:
        sqlite_conn = sqlite3.connect(DB_SQLITE_PATH)
    except sqlite3.Error, e:
         print 'conntect sqlite database failed.'
         sqlite_logger.error("conntect sqlite database failed, ret = %s" % e.args[0])   
         return
    
    sqlite_logger.info("conntect sqlite database(%s) succ." % DB_SQLITE_PATH)
    
    #获取游标
    sqlite_cursor = sqlite_conn.cursor()
   
    #删除表
    sql_desc2 = "DROP TABLE IF EXISTS tbl_product3;"
    try:
        sqlite_cursor.execute(sql_desc2)
    except sqlite3.Error, e:
         print 'drop table failed'
         sqlite_logger.error("drop table failed, ret = %s" % e.args[0])
         sqlite_cursor.close()
         sqlite_conn.close()     
         return
    sqlite_conn.commit()   
   
    sqlite_logger.info("drop table(tbl_product3) succ.")
   
    #建表
    sql_desc = '''CREATE TABLE tbl_product3(
    i_index INTEGER PRIMARY KEY,
    sv_productname VARCHAR(32)
    );'''
    try:
        sqlite_cursor.execute(sql_desc)
    except sqlite3.Error, e:
         print 'drop table failed.'
         sqlite_logger.error("drop table failed, ret = %s" % e.args[0])
         sqlite_cursor.close()
         sqlite_conn.close()   
         return
    sqlite_conn.commit()
   
    sqlite_logger.info("create table(tbl_product3) succ.")
   
    #插入记录
    sql_desc = "INSERT INTO tbl_product3(sv_productname) values('apple')"
    try:
        sqlite_cursor.execute(sql_desc)
    except sqlite3.Error, e:
        print 'insert record failed.'
        sqlite_logger.error("insert record failed, ret = %s" % e.args[0]) 
        sqlite_cursor.close()
        sqlite_conn.close()   
        return
    sqlite_conn.commit()
   
    sqlite_logger.info("insert record into table(tbl_product3) succ.")
   
    #查询记录
    sql_desc = "SELECT * FROM tbl_product3;"
    sqlite_cursor.execute(sql_desc)
    for row in sqlite_cursor:
        print row
        sqlite_logger.info("%s", row)
   
    #关闭游标和数据库句柄   
    sqlite_cursor.close()
    sqlite_conn.close()
   
    sqlite_logger.debug("operate_sqlite3_tbl_product leaving...")

if __name__ == '__main__':
   
    #初始化日志系统
    sqlite_logger = log_init(LOG_FILENAME, LOG_CONTENT_NAME)  
   
    #操作数据库
    operate_sqlite3_tbl_product()
```

## Python的50个模块，满足你各种需要

Graphical interface wxPython http://wxpython.org   
Graphical interface pyGtk http://www.pygtk.org   
Graphical interface pyQT http://www.riverbankcomputing.co.uk/pyqt/   
Graphical interface Pmw http://pmw.sourceforge.net/   
Graphical interface Tkinter 3000 http://effbot.org/zone/wck.htm   
Graphical interface Tix http://tix.sourceforge.net/   
        
Database MySQLdb http://sourceforge.net/projects/mysql-python   
Database PyGreSQL http://www.pygresql.org/   
Database Gadfly http://gadfly.sourceforge.net/   
Database SQLAlchemy http://www.sqlalchemy.org/   
Database psycopg http://www.initd.org/pub/software/psycopg/   
Database kinterbasdb http://kinterbasdb.sourceforge.net/   
Database cx_Oracle http://www.cxtools.net/default.aspx?nav=downloads   
Database pySQLite http://initd.org/tracker/pysqlite   
        
MSN Messenger msnlib http://auriga.wearlab.de/~alb/msnlib/   
MSN Messenger pymsn http://telepathy.freedesktop.org/wiki/Pymsn   
MSN Messenger msnp http://msnp.sourceforge.net/   
Network Twisted http://twistedmatrix.com/   
Images PIL http://www.pythonware.com/products/pil/   
Images gdmodule http://newcenturycomputers.net/projects/gdmodule.html   
Images VideoCapture http://videocapture.sourceforge.net/   
        
Sciences and Maths scipy http://www.scipy.org/   
Sciences and Maths NumPy http://numpy.scipy.org/   
Sciences and Maths numarray http://www.stsci.edu/resources/software_hardware/numarray   
Sciences and Maths matplotlib http://matplotlib.sourceforge.net/   
        
Games Pygame http://www.pygame.org/news.html   
Games Pyglet http://www.pyglet.org/   
Games PySoy http://www.pysoy.org/   
Games pyOpenGL http://pyopengl.sourceforge.net/   
        
Jabber jabberpy http://jabberpy.sourceforge.net/   
        
Web scrape http://zesty.ca/python/scrape.html   
Web Beautiful Soup http://crummy.com/software/BeautifulSoup   
Web pythonweb http://www.pythonweb.org/   
Web mechanize http://wwwsearch.sourceforge.net/mechanize/   
        
Localisation geoname.py http://www.zindep.com/blog-zindep/Geoname-python/   
        
Serial port pySerial http://pyserial.sourceforge.net/   
Serial port USPP http://ibarona.googlepages.com/uspp   
        
Parallel Port pyParallel http://pyserial.sourceforge.net/pyparallel.html   
        
USB Port pyUSB http://bleyer.org/pyusb/   
        
Windows ctypes http://starship.python.net/crew/theller/ctypes/   
Windows pywin32 http://sourceforge.net/projects/pywin32/   
Windows pywinauto http://www.openqa.org/pywinauto/   
Windows pyrtf http://pyrtf.sourceforge.net/   
Windows wmi http://timgolden.me.uk/python/wmi.html   
        
PDA/GSM/Mobiles pymo http://www.awaretek.com/pymo.html   
PDA/GSM/Mobiles pyS60 http://sourceforge.net/projects/pys60   
        
Sound pySoundic http://pysonic.sourceforge.net/   
Sound pyMedia http://pymedia.org/   
Sound FMOD http://www.fmod.org/   
Sound pyMIDI http://www.cs.unc.edu/Research/assist/developer.shtml   
        
GMail libgmail http://libgmail.sourceforge.net/   
Google pyGoogle http://pygoogle.sourceforge.net/   
Expect pExpect http://pexpect.sourceforge.net/   
WordNet pyWordNet http://osteele.com/projects/pywordnet/   
Command line cmd http://blog.doughellmann.com/2008/05/pymotw-cmd.html   
Compiler backend llvm-py http://mdevan.nfshost.com/llvm-py/   
3D VPython http://vpython.org

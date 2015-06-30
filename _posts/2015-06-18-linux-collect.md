---
layout: post
title: linux 收集
category: linux
comments: false
---

### 常用命令

+ 文件命令

`ls` – 列出目录  
`ls -al` – 使用格式化列出隐藏文件  
`cd dir` - 更改目录到 dir  
`cd` – 更改到 home 目录  
`pwd` – 显示当前目录  
`mkdir dir` – 创建目录 dir  
`rm file` – 删除 file  
`rm -r dir` – 删除目录 dir  
`rm -f file` – 强制删除 file  
`rm -rf dir` – 强制删除目录 dir *  
`cp file1 file2` – 将 file1 复制到 file2  
`cp -r dir1 dir2` – 将 dir1 复制到 dir2; 如果 dir2 不存在则创建它  
`mv file1 file2` – 将 file1 重命名或移动到 file2; 如果file2 是一个存在的目录则将 file1 移动到目录 file2 中  
`ln -s file link` – 创建 file 的符号连接 link  
`touch file` – 创建 file  
`cat > file` – 将标准输入添加到 file  
`more file` – 查看 file 的内容  
`head file` – 查看 file 的前 10 行  
`tail file` – 查看 file 的后 10 行  
`tail -f file` – 从后 10 行开始查看 file 的内容  

+ 进程管理

`ps`– 显示当前的活动进程  
`top` – 显示所有正在运行的进程  
`kill pid` – 杀掉进程 id pid  
`killall proc` – 杀掉所有名为 proc 的进程 *  
`bg` – 列出已停止或后台的作业  
`fg` – 将最近的作业带到前台  
`fg n`– 将作业 n 带到前台  

+ 文件权限

`chmod octal file` – 更改 file 的权限  
- 4 – 读 (r)  
- 2 – 写 (w)  
- 1 – 执行 (x)  
示例:  
`chmod 777 `– 为所有用户添加读、写、执行权限  
`chmod 755 `– 为所有者添加 rwx 权限, 为组和其他用户添加rx 权限  
更多选项参阅 man chmod.

+ SSH

`ssh user@host` – 以 user 用户身份连接到 host  
`ssh -p port user@host` – 在端口 port 以 user 用户身份连接到 host  
`ssh-copy-id user@host` – 将密钥添加到 host 以实现无密码登录  

+ 搜索

`grep pattern files` – 搜索 files 中匹配 pattern 的内容  
`grep -r pattern dir` – 递归搜索 dir 中匹配 pattern 的内容  
`command | grep pattern` – 搜索 command 输出中匹配pattern 的内容  

+ 系统信息

`date` – 显示当前日期和时间  
`cal` – 显示当月的日历  
`uptime` – 显示系统从开机到现在所运行的时间  
`w` – 显示登录的用户  
`whoami` – 查看你的当前用户名  
`finger user` – 显示 user 的相关信息  
`uname -a` – 显示内核信息  
`cat /proc/cpuinfo` – 查看 cpu 信息  
`cat /proc/meminfo` – 查看内存信息  
`man command` – 显示 command 的说明手册  
`df` – 显示磁盘占用情况  
`du` – 显示目录空间占用情况  
`free` – 显示内存及交换区占用情况  

+ 压缩

`tar cf file.tar files` – 创建包含 files 的 tar 文件file.tar  
`tar xf file.tar` – 从 file.tar 提取文件  
`tar czf file.tar.gz files` – 使用 Gzip 压缩创建tar 文件  
`tar xzf file.tar.gz` – 使用 Gzip 提取 tar 文件  
`tar cjf file.tar.bz2` – 使用 Bzip2 压缩创建 tar 文件  
`tar xjf file.tar.bz2` – 使用 Bzip2 提取 tar 文件  
`gzip file` – 压缩 file 并重命名为 file.gz  
`gzip -d file.gz` – 将 file.gz 解压缩为 file  

+ 网络

`ping host` – ping host 并输出结果  
`whois domain` – 获取 domain 的 whois 信息  
`dig domain` – 获取 domain 的 DNS 信息  
`dig -x host` – 逆向查询 host  
`wget file` – 下载 file  
`wget -c file` – 断点续传  

+ 安装

从源代码安装:  
`./configure`  
`make`  
`make install`  

`dpkg -i pkg.deb` – 安装包 (Debian)  
`rpm -Uvh pkg.rpm` – 安装包 (RPM)  

+ 快捷键

<kbd>Ctrl+C</kbd> – 停止当前命令
<kbd>Ctrl+Z</kbd> – 停止当前命令，并使用 fg 恢复
<kbd>Ctrl+D</kbd> – 注销当前会话，与 exit 相似
<kbd>Ctrl+W</kbd> – 删除当前行中的字
<kbd>Ctrl+U</kbd> – 删除整行
<kbd>!!</kbd> - 重复上次的命令
<kbd>exit</kbd> – 注销当前会话

### yakuake

修改yakuake字体

yakuake设置字体只能选择系统自带字体，自定义字体可通过修改配置文件实现

打开配置文件 （`xxx`为对应配置方案名）：

```
vi ～/.kde/share/apps/konsole/xxx.profile
```

修改`Font=Fantasque Sans Mono,11,-1,5,50,0,0,0,0,0`一句。

>[Appearance]  
>AntiAliasFonts=true  
>ColorScheme=GreenOnBlack  
>Font=Fantasque Sans Mono,11,-1,5,50,0,0,0,0,0  

>[General]  
>Environment=TERM=xterm  
>LocalTabTitleFormat=%d : %n  
>Name=xterm  
>Parent=FALLBACK/  


### zsh

 

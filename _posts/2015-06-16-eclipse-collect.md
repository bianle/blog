---
layout: post
title: eclipse收集
category: java
comments: false
---

### svn 插件

* Malformed network data

>Malformed network data
>Unable to Parse URL '/svn/root/!svn/bc/749/EIP/EIP2/dwcode/code.doc

`window - preferences - team - svn - svn接口` 将client 选项`JavaHL(JNI)1.6.4(r38063)`改为`SVNKit(Pure Java)SVNKit v1.3.0.5847`

### kubuntu 下 eclipse 崩溃

eclipse崩溃是因为gtk2-egines-oxygen的1.4.4版本引入了一个bug
找到 GTK2 主题配置文件 ， oxygen 为 `/usr/share/themes/oxygen-gtk/gtk-2.0/gtkrc`
以管理员身份编辑将`GtkComboBox::appears-as-list`选项`1`改为`0`

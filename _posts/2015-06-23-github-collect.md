---
layout: post
title: github 收集
category: git
comments: false
---

## ubuntu 搭建 jekyll 环境

* 安装 Ruby 环境

```
sudo apt-get install ruby ruby-dev
```

* 更换Gem sources (国外源太慢，此步骤不必须)

```
sudo gem sources –remove https://rubygems.org/  
sudo gem sources –remove http://rubygems.org/  
sudo gem sources -a http://ruby.taobao.org/  
```

可以看一下是否更换成功

```
sudo gem sources -l
```

>*** CURRENT SOURCES ***
>
> http://ruby.taobao.org

* 安装Jekyll 和 markdown支持

```
sudo gem install jekyll  
sudo gem install rdiscount
```

现在你可以在你clone出来的目录下运行jekyll serve来启动服务器，然后浏览器打开 http://127.0.0.1:4000 即可检查你的github pages的效果了


## git clone 到一个非空文件夹

1. 进入非空目录，假如～/git/trunk
2. `git clone --no-checkout https://github.com/bianle/trunk.git tmp`  
3. `mv tmp/.git . # 将tmp下.git目录移到当前目录 `  
4. `rmdir tmp`  
5. `git reset --hard HEAD`  

## git
### 修改远程仓库

` git remote set-url --push [name] [newUrl]`

### 默认用户名密码

1. ~/下

```
touch .git-credentials
vim .git-credentials
htts:*username*:*password*@github.com
```

2. 终端下执行命令

```
git config --global credential.helper store
```

3. ~/.gitconfig文件下多了一项

>[credential]
>  helper=store


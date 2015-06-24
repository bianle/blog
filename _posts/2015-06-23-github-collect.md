---
layout: post
title: github 收集
category: git
comments: false
---

## git clone 到一个非空文件夹

1. 进入非空目录，假如～/git/trunk
2. `git clone --no-checkout https://github.com/bianle/trunk.git tmp`
3. `mv tmp/.git . # 将tmp下.git目录移到当前目录 `
4. `rmdir tmp`
5. `git reset --hard HEAD`

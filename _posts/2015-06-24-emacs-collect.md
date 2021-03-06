---
layout: post
title: emacs 收集
category: emacs
comments: false
---

## 安装 purcell 的 emacs.d 配置文件

1. `git clone https://github.com/purcell/emacs.d.git ~/.emacs.d`
2. 删除 ~/.emacs文件
3. 重启 emacs

## emacs 替换

<kbd>M-% RET</kbd> 输入被替换的词如`trunk`<kbd>RET</kbd>输入要替换的词如`dotfiles`
<kbd>RET</kbd>,按<kbd>y</kbd>替换并跳到下一个词，按<kbd>n</kbd>不替换跳到下个词。

## emacs 的 org-mode

* 介绍

Org mode 是 emacs 中的一个主模式。 Org 是 organization 的缩写。这个模式的主要作用是用来记笔记，写 todo list，org mode 有一个目标，就是希望每件事情都只记录一次。 Org mode 已经是 emacs 自带的组件，如果你想用最新的 org mode，可以在这里下载：[http://orgmode.org/](http://orgmode.org/)

* 基本用法

很简单，一级标题用一个星号（*）表示，二级标题用两个星号表示，三级标题用三个星号表示，以此类推，注意，星号必须顶格写。比如，新建一个文件，在 emacs 中打开，然后敲：<kbd>M-x org-mode</kbd>，进入 org mode。然后，在文件中写入下面的内容：

>\* top level  
>** the secondlevel  
>*** the third level  
>somethings to write  

_注意，星号前不能有空格。_

然后你就可以看到这些各个等级的标题和内容被合适的高亮了。你可以自由的控制这些标题和内容是被折叠还是显示出来，方法就是使用TAB。比如，把光标移动到最开始的地方，连续按TAB, org mode 会把 buffer 中的内容按照 全部折叠->展开所有标题->展开全部内容 这样的顺序循环显示。另外，还有一些快捷键可以帮助你方便的在 org mode 中间移动:

<kbd>C-c C-n</kbd> 移动到下一个标题  
<kbd>C-c C-p</kbd> 移动到上一个标题  
<kbd>C-c C-f</kbd> 移动到和当前标题同等级的下一个标题  
<kbd>C-c C-b</kbd> 移动到和当前标题同等级的上一个标题  
<kbd>C-c C-u</kbd> 向上移动到更高一层的标题  

* 列表

在同一个子标题下，你还可以将内容划分的更细致。方法是使用这些符号： ‘-’， ‘+’， ‘\*’， ‘1.’， ‘1)’。注意，这里的’*'不能顶格写。 比如，在你的 org mode 的 buffer 中写入下面这些文字（从 org mode 手册上抄来的例子）：

> My favorite scenes are (in this order)  
> 1. The attack of the Rohirrim  
> 2. Eowyn’s fight with the witch king  
> * this was already my favorite scene in the book  
> * I really like Miranda Otto.  
> 3. Peter Jackson being shot by Legolas  
> – on DVD only  
> He makes a really funny face when it happens.  
> But in the end, no individual scenes matter but the film as a whole.  
> Important actors in this film are:  
> – Elijah Wood :: He plays Frodo  
> – Sean Austin :: He plays Sam, Frodo’s friend. I still remember  
> him very well from his role as Mikey Walsh in the Goonies.  

把光标移动到 ‘1.’ ‘2.’ 或 ‘3.’ 所在的行上，然后按 shift 加左右方向键，看看有什么效果。

* 注脚

在 org mode 中，你可以为你的文章添加注脚（footnote）。注脚的格式有两种，一是方括号+数字，二是方括号+fn+名字。比如下面有两个例子：
在 org mode 的正文中写下这两句话：

> The Org homepage[1] now looks a lot better than it used to.  
> The Org homepage[fn:orghome] now looks a lot better than it used to.


接下俩你可以写一些其他东西，然后在文章的末尾写上下面两句话（注意：必须要顶格写）：


> [1] The link is: http://orgmode.org  
> [fn:orghome] The link is: http://orgmode.org


把光标移动到正文的`[1]`处，按<kbd> C-c C-c</kbd>，可以跳转到注脚`[1]`处，在注脚`[1]`处按<kbd> C-c C-c</kbd>，可以跳转到正文的`[1]`处。对于`[2]`也一样。

* 表格

1. 基本表格
在 org mode 中，你可以画表格。方法很简单，在某一行顶格的位置输入`|`，然后输入表格第一行第一列的内容，再输入`|`，然后输入表格的的一行第二列的内容，以此类推。当第一行输入完成后，按 TAB， org mode 会把当前行对齐，然后为你创造出表格的下一行，让你继续输入。用 TAB 和 S+TAB可以在表格中正向或反向的移动。
比如下面的例子：

>| 1 | one |  
>| 2 | two |  
>| 3 | This is a long chunk of text |  
>| 4 | four |  


有时候，表格的某一项特别的长，你可以在任意一个表格的空白项内输入数字来限制其所在列的长度。比如：

>| | <6> |  
>| 1 | one |  
>| 2 | two |  
>| 3 | This=> |  
>| 4 | four |  


在上面的例子中，第二列的长度不会超过6，超过的部分会被隐藏起来。你可以把鼠标移动到`This=>`上面，会有浮动标签显示出该项的内容，或者输入<kbd>C-c</kbd>可以编辑被隐藏的内容。如果你发现表格的某一项没有如你预期的那样被隐藏起来，你可以在表格中的任意位置按 <kbd>C-c C-c</kbd>。

我们还可以把表格弄的更好看一些。在表格的某两行之间加入`|-`，然后按<kdb>TAB</kbd>，可以作出下面的效果：

>| | <6> |  
>|—+——–|  
>| 1 | one |  
>| 2 | two |  
>| 3 | This=> |  
>| 4 | four |  

2. spreadsheet

你可以在表格中增加计算的功能。比如你有这样一个表格：第一列和第二列是三角形两个直角边的长度，第三列是通过勾股定理计算出的斜边长度，或者是这样一张表格：前几列是你各个科目的考试成绩，最后一列是你的平均分，那么，org mode 可以自动为你完成计算。我们以计算勾股定理为例，创建一张表格：

>| a | b | |  
>|—+—+—|  
>| 3 | 4 | |  
>| 6 | 8 | |  
>| 1 | 2 | |  


在第二行第三列中输入`=($1^2+$2^2)^0.5`。$1和$2表示第一列和第二列，在你自己的表格中，你会使用你希望的列数进行计算。

>| a | b | |  
>|—+—+———–|  
>| 3 | 4 | 5. |  
>| 6 | 8 | 10. |  
>| 1 | 2 | 2.2360680 |  
>\#+TBLFM: $3=($1^2+$2^2)^0.5

有时候，你输入完成后，org mode不会自动帮你对所有项都计算一边，这时，你可以输入<kbd>C-u C-c C-c</kbd>强制org mode为整个表格进行计算。如果你不想让某一列都按照公式计算，只希望在某一特定项上进行计算，你可以这样输入：`:=($1^2+$2^2)^0.5`, 即在等号前再加一个冒号。

* 链接
你可以在 org mode 中设置连接。比如链接到当前文档的某个位置，或者链接到一个外部文件。链接到当前文档的某个位置只需这样输入：

>\[[link][description]]

description 是你希望链接到的内容，org mode 会通过字符串搜索的方式找到第一个与 description 匹配的地方作为链接的目标。要链接到一个外部文件，只需这样输入：


>\[[file:/directory/filename]]


想要跳到链接处，可以使用快捷键<kbd>C-c C-o</kbd>，或者鼠标左键单击。对于到外部文件的链接，emacs 会试图将该文件打开。

* todo list

Org mode 的一个很重要的功能就是写 todo list。 创建一个todo list 和创建一个 org mode 的其他标题并没有什么区别。比如你可以顶格写下这样一
行：


> \* write org mode document


然后把光标移动到这行上，按shift+左右方向键，你会发现该条目会在 TODO和 DONE 之间切换：

> \* TODO write org mode document  
> \* DONE write org mode document


这就是最简单的 todo list 了，想做什么，就在 org mode 下创建一个条目，并将其状态设置为 `TODO`，做完后将其状态设置为 `DONE`。

todo list 默认只有两种状态： `TODO` 和 `DONE`。我们可以再增加一些其他状态。比如在.emacs中加入下面的语句:

``` lisp
(setq org-todo-keywords
      '((sequence "TODO" "DOING" "HANGUP" "|" "DONE" "CANCEL")))
```

可以将 todo list 的状态增加为五种：TODO，DOING，HANGUP，DONE，CANCEL。
注意，在 HANGUP 和 DONE 之间有一条竖线 “|”，在竖线之前的状态和之后的状态使用的是不同的face。

进一步的，我们还可以记录切换到某一种状态时的时间：

``` lisp
(setq org-todo-keywords
      '((sequence "TODO(t)" "DOING(i!)" "HANGUP(h!)" "|" "DONE(d!)" "CANCEL(c!)")))
```

在 `DOING` `HANGUP` `DONE` 和 `CANCEL` 后的括号中都有一个`!`，这种写法表示进入到这些状态后会记录下当前时间。而每个状态后面的括号中都有一个字母，这个字母就是进入该状态的快捷键。当你想切换到某一状态时，可以输入<kbd>C-c C-t</kbd>（此时 emacs 会弹出一个提示窗口），然后输入对应的快捷键就可以把当前的条目设置为该状态。

注意，改变 org-todo-keywrods 之后，最好把你的 org mode 的文件关闭，然后在重新打开，否则可能会发现设置无效。

8. 导出成其他格式

已经编辑好的 org mode 文档可以导出为其他格式。  
<kbd>C-c C-e a</kbd> 导出为文本文件。  
<kbd>C-c C-e h</kbd> 导出为 HTML 文件。

9. 请参考 org-mode 的文档

实际上 org mode 提供的功能极其繁多。我在上面列出的只是我日常会用到的功能。 Org mode 还有很多我这篇文章中完全没有涉及的功能，而我涉及到的功能，也有许多细微的技巧没有提及。幸运的是， org mode 有一份极其详尽的文档。你可以在 emacs 中输入 <kbd>C-h i</kbd>，然后搜索 `org mode`，找到该文档。想充分发挥出 org mode 的威力，需要仔细阅读这份文档。

## emacs 打开文件乱码

尝试修改文件编码
<kbd>M-x revert-buffer-with-coding-system RET</kbd>
输入编码如`chinese-gkb`<kbd>RET</kbd>
保存时指定编码
<kbd>M-x set-buffer-file-coding-system RET</kbd>
输入编码如`utf-8`<kbd>RET</kbd>

设置默认编码

```lisp
(set-language-environment "UTF-8")
(set-terminal-coding-system 'utf-8)
(set-keyboard-coding-system 'utf-8)
(set-clipboard-coding-system 'utf-8)
(set-buffer-file-coding-system 'utf-8)
(set-selection-coding-system 'utf-8)
(modify-coding-system-alist 'process "*" 'utf-8)
```

## emacs 末行去空格

whitespace-cleanup-mode

## emacs html

预览: <kbd>C-c C-v</kbd>

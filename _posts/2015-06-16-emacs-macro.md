---
layout: post
title: emacs宏
category: emacs
comments: false
---

* 录制

按下组合键<kbd>C-x (</kbd>开始录制宏，emacs会将所有按键动作记录下来，按下组合键<kbd>C-x )</kbd>结束录制

* 回放

按下组合键<kbd>C-x e</kbd>(命令为：call-last-kbd-macro)回放

* 命名

输入<kbd>M-x name-last-kbd-macro</kbd>,按下<kbd>RETURN</kbd>，输入名字，再按下<kbd>RETURN</kbd>。
接下来就可以通过<kbd>M-x *name*</kbd>回放宏

* 保存

输入<kbd>C-x C-f *name*</kbd> 打开一个文件，<kbd>M-></kbd>移动到文件尾，输入<kbd>M-x insert-kbd-macro RETURN</kbd> 输入 *macroname*，然后按下<kbd>RETURN</kbd> emacs 将宏的lisp代码插入当前位置。

如：

>(fset 'mac-test
>   [?- ?- ?- return ?l ?a ?y ?o ?u ?t ?: ?  ?p ?o ?s ?t return ?t ?i ?t ?l ?e ?: ?  return ?c ?a ?t ?e ?g ?o ?r ?y ?: return ?c ?o ?m ?m ?e ?n ?t ?s ?: ?  ?f ?a ?l ?s ?e return ?- ?- ?- ?\C-p ?\C-p ?\C-p ?\C-e ? ])

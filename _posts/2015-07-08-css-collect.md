---
layout: post
title: css收集
category: html
comments: false
---

## css3 阴影

```
<!DOCTYPE html> <html>
 <head>
 <title>盒子阴影</title>
 <meta charset="utf-8" />
 <style>
 .box {
 width:300px;
 height:300px;
 background-color:#fff;
 border:1px solid #93d4fc;         /* 设置阴影 */
 /* -webkit-box-shadow:1px 1px 3px #292929;
 -moz-box-shadow:1px 1px 3px #292929;
 box-shadow:1px 1px 3px #292929;*/
 box-shadow:0px 0px 5px #60caff;
 }
 </style>
 </head>
 <body>
 <div>
 <br /><br /><br /><br />
 看，是不是很有立体感？没有设置边框啊。
 </div>
 </body>
 </html>
```

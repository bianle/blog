---
layout: html
title: js代码收集
category: html
comments: false
---

### js 扩展方法
* startWith , endWith

```
String.prototype.endWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

String.prototype.startWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }
```

* trim ltrim rtrim

```
String.prototype.trim=function(){
　　return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim=function(){
　　return this.replace(/(^\s*)/g,"");
}
String.prototype.rtrim=function(){
　　return this.replace(/(\s*$)/g,"");
}
```

### js setTimeout
```
<script>
        function doTimer(btnId,timeId,delayTimes){

                var delayBtn = document.getElementById(btnId);
                var hideTime = document.getElementById(timeId);
                if(hideTime.innerHTML*1==1){
                        delayBtn.disabled=false;
                        delayBtn.value = "获取短信验证码";
                        hideTime.innerHTML = delayTimes;
                }else{
                        delayBtn.disabled=true;
                        hideTime.innerHTML = (hideTime.innerHTML*1-1);
                        delayBtn.value =hideTime.innerHTML +" 秒后获取短信验证码";
                        window.setTimeout("doTimer('"+btnId+"','"+timeId+"','"+delayTimes+"')",1000);
                }

        }
</script>
<!-- 三个参数 1、ID为delayTime的隐藏DIV；2、ID为delayBtn的button；3、隐藏DIV的innerHTML 20秒 -->
<div id="delayTime" style="display:none;">20</div>
<input type="button" id="delayBtn" style="width:180px;" onclick="doTimer('delayBtn','delayTime','20');" value="获取短信验证码"></input>
```

### js call

```
<script>

function MyObj(){  }

MyObj.prototype.myMethod = function(){

alert(this);

}

function test(){

new MyObj().myMethod(1);

MyObj.prototype.myMethod(1);

MyObj.prototype.myMethod.call(1);

}

</script>
```
结果
>[object Object]
>[object Object]
>1

### js 拖动
```
<html xmlns="<a href="http://www.w3.org/1999/xhtml">http://www.w3.org/1999/xhtml</a>">

<head runat="server">

<title></title>

<script language=javascript>

var intX;

var intY;

var blnDrag = false; //鼠标是否已经按下

//鼠标按下

function MouseDown(event,id) {

//鼠标按下

blnDrag = true;

intX = event.clientX - GetDiv(id).style.pixelLeft;

intY = event.clientY - GetDiv(id).style.pixelTop;

}

//鼠标拖动

function DragDiv(event,id) {

//判断鼠标是否已经按下

if (!blnDrag) {

return false;

}

else {

GetDiv(id).style.pixelLeft = event.clientX - intX;

GetDiv(id).style.pixelTop = event.clientY - intY;

}

}

//鼠标松开时

function mouseUp() {

//鼠标没有按下

blnDrag = false;

}

//得到DIV

function GetDiv(id) {

return document.getElementById(id);

}

</script>

</head>

<body >

<form id="form1" runat="server">

<div id="div1" style="CURSOR: move; POSITION: absolute;  WIDTH: 140px; HEIGHT: 112px; background-color:Red"     onmousemove="DragDiv(event,this.id)"     onmousedown="MouseDown(event,this.id)"     onmouseup="mouseUp()"></div>

</form>

</body>

</html>


```

### js 回调函数

```
<html>

<head>

<title>回调函数(callback)</title>

<script language="javascript" type="text/javascript">

function a(callback)  {

alert("我是parent函数a！");

alert("调用回调函数");

callback();

}

function b(){

alert("我是回调函数b");

}

function c(){

alert("我是回调函数c");

}

function test()  {

a(b);

a(c);

}

</script>

</head>

<body>

<h1>学习js回调函数</h1>

<button onClick=test()>click me</button>

<p>应该能看到调用了两个回调函数</p>

</body>

</html>
```

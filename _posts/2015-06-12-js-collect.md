---
layout: post
title: js代码收集
category: html
comments: false
---

### js 扩展方法
* startWith , endWith

```js
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

```js
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
```html
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

<a id="toggleComment" href="javascript:void(0);" onclick="showComment()">展开评论&gt;&gt;</a>
<script>
function showComment(){
    document.getElementById('disqus_thread').style.display='block';
    document.getElementById('toggleComment').innerHTML="&lt;&lt;收起评论";
    document.getElementById('toggleComment').setAttribute("onclick","hideComment()");
}
function hideComment(){
    document.getElementById('disqus_thread').style.display='none';
    document.getElementById('toggleComment').innerHTML="展开评论&gt;&gt;";
    document.getElementById('toggleComment').setAttribute("onclick","showComment()");
}
</script>
<div id="disqus_thread" style="display:none;"></div>
 <script type="text/javascript">
     /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
     var disqus_shortname = '{{site.disqusname}}'; // required: replace example with your forum shortname

     /* * * DON'T EDIT BELOW THIS LINE * * */
     (function () {
         var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
         dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
         (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
     })();
 </script>
 <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    <a style="display:none"  href="http://disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
    

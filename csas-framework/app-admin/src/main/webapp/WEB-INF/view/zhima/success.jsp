<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<html>
    <head>
         <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <meta http-equiv="Expires" CONTENT="-1">
        <meta http-equiv="Cache-Control" CONTENT="no-cache">
        <meta http-equiv="Pragma" CONTENT="no-cache">
    </head>
    <style>
         body
        {
            margin:0;
            padding:0;
            background-color:#f7f8fd;
        }
        .content
        {
            width:90%;
            margin:10rem auto 2rem auto;
            text-align:center;
        }
        .word
        {
            width:90%;
            margin:0 auto;
            text-align:center;
            font-size:1.2rem;
            color:#666;
        }
        .word p{
            margin-bottom:0.5rem;
        }
        .time
        {
            font-size:0.8rem;
        }
        .button
        {
            width:80%;
            height: 3rem;
            margin:2rem auto 0 auto;
            
        }
        .button  input 
        {
            width:100%;
            height:3rem;
            border:none;
            outline:none;
            font-size:1.2rem;
            border-radius:0.5rem;
            background-color:#148612;
            color:#fff;
        }
    </style>
    <body>
          <div class="content">
            <img src="<%=basePath%>resources/img/success.png"/>   
        </div>
        <div class="word">
            <p>认证成功</p>
            
        </div>
        <div class="button">
                <input type="button" value="返 回" onclick="displaymessage()" >
        </div>
        <script>
            function displaymessage() {
                var u = navigator.userAgent, app = navigator.appVersion;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1;
                var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
                if(isAndroid){
                     Android.changeActivity("成功");
                 }
                 if(isIOS){
                     window.webkit.messageHandlers.submitResult.postMessage("成功");
                         }
           
                }
           </script>
    </body>
</html>
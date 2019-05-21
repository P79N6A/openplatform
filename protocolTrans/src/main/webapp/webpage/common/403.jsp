<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>403</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<style>
html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,dl,dt,dd,ol,nav ul,nav li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline;}
article, aside, details, figcaption, figure,footer, header, hgroup, menu, nav, section {display: block;}
ol,ul{list-style:none;margin:0px;padding:0px;}
blockquote,q{quotes:none;}
blockquote:before,blockquote:after,q:before,q:after{content:'';content:none;}
table{border-collapse:collapse;border-spacing:0;}
/*--start editing from here--*/
a{text-decoration:none;}
img{max-width:100%;}
/*--end reset--*/
body {

    font-family: 'Rubik Mono One', sans-serif;
    /* background: url(images/banner.jpg)no-repeat 0px 0px; */
    background-size: cover;
    background-attachment: fixed;
    background-color:#863ca2;
}
/*-- main --*/
.main {
    padding: 10% 0 7%;
}
.error-page h1 {
    font-size: 10em;
    text-align: center;
    color: #fff;
    line-height: 1.1em;
    text-shadow: 3px 4px 4px #000;
    letter-spacing: 1px;
}
.error-page p {
    text-align: center;
    margin-top: 0.5em;
    font-size: 2em;
    color: #000000;
    text-shadow: 2px 0px;
    letter-spacing: 2px;
}
/*---- responsive-design -----*/
@media(max-width:1440px){
.main {
    padding: 10% 0 5%;
}
}
@media(max-width:1080px){
.main {
    padding: 13% 0 5%;
}
.error-page h1 {
    font-size: 8em;
}
.error-page p {
    font-size: 1.8em;
}
}
@media(max-width:1024px){
.main {
    padding: 13% 0 10%;
}
}
@media(max-width:991px){
.error-page h1 {
    font-size: 7em;
    letter-spacing: 2px;
    text-shadow: 2px 3px 4px #000;
}
.error-page p {
    font-size: 1.7em;
    letter-spacing: 1px;
}
}
@media(max-width:768px){
.main {
    padding: 19% 0 10%;
}
}
@media(max-width:640px){
.main {
    padding: 18% 0 10%;
}
.error-page h1 {
    font-size: 6em;
}
.error-page p {
    font-size: 1.5em;
}
}
@media(max-width:480px){
.main {
    padding: 26% 0 10%;
}
.error-page h1 {
    font-size: 4.5em;
}
.error-page p {
    font-size: 1.2em;
}
}
@media(max-width:320px){
.main {
    padding: 33% 0 10%;
}
.error-page h1 {
    font-size: 3em;
}
.error-page p {
    font-size: 0.9em;
	margin-top: 1em;
}
}
</style>
</head>
<body>
	<div class="main">
		<div class="error-page">
			<h1>403<br>ERROR</h1>
			<p>您无权访问该请求。。。</p>
		</div>	
	</div>
</body>
</html>
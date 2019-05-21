<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
</style>
<script>
        $(document).ready(function(){
        	$('#smartwizard').smartWizard({
                lang: {  // Language variables
                    next: '下一步', 
                    previous: '上一步'
                },
                toolbarSettings: [{
                    toolbarPosition: 'bottom', // none, top, bottom, both
                    toolbarButtonPosition: 'left', // left, right
                    showNextButton: true, // show/hide a Next button
                    showPreviousButton: true, // show/hide a Previous button
                }, {
                    toolbarPosition: 'top', // none, top, bottom, both
                    toolbarButtonPosition: 'right', // left, right
                    showNextButton: true, // show/hide a Next button
                    showPreviousButton: true, // show/hide a Previous button
                }],
                transitionEffect: 'fade', // Effect on navigation, none/slide/fade
          });
        });
        
</script>
</head>
<body>
    <div id="smartwizard">
    <ul>
        <li><a href="#step-1">Step Title<br /></a></li>
        <li><a href="#step-2">Step Title<br /></a></li>
        <li><a href="#step-3">Step Title<br /></a></li>
        <li><a href="#step-4">Step Title<br /></a></li>
    </ul>
 
    <div>
        <div id="step-1" class="">
            <input type="text" />
        </div>
        <div id="step-2" class="">
            <input type="text" />
        </div>
        <div id="step-3" class="">
            <input type="text" />
        </div>
        <div id="step-4" class="">
            <input type="text" />
        </div>
    </div>
</div>
</body>
</html>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Multi Data source routing</title>
	<base href="${pageContext.request.contextPath}/" />
	<script src="web-resources/jquery-3.3.1.min.js"></script>
	<script src="web-resources/bootstrap.min.js"></script>
	<link rel="stylesheet" href="web-resources/bootstrap.min.css" type="text/css">
    
</head>

<body>
	
	
	<div class="container">
		<br/>
		<button class="btn btn-primary" id="btn_saral"> File Storage-Saral Satelite </button>
		<button class="btn btn-success" id="btn_aryabhat"> File Storage-Arayabhat Satelite </button>
	</div>

</body>

<script>
     var ajax={
	     invokeAjax :function(URL, jsonData, requestType) {
		     var responseData=null;
		     $.ajax({
		     type : requestType,
		     url : basepath+URL,
		     contentType:"application/json",
		     async : false,
		     data: JSON.stringify(jsonData),
		     success : function(data) {
		     responseData=data;
		     
		     },
		     error : function(data) {
		     responseData =data;
		     }
		     });
		     return responseData;
	     },
     };
		var basepath="${pageContext.request.contextPath}/";
</script>
<script>

$(function() {

	$('#btn_saral').on('click',function(){
		var response = ajax.invokeAjax("files?type=SARAL", null, "GET");
		console.log(response);
		window.open(response);
	});


	$('#btn_aryabhat').on('click',function(){
		var response = ajax.invokeAjax("files?type=Aryabhat", null, "GET");
		console.log(response);
		window.open(response);
	});
	
});
</script>

</html>
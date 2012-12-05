<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
    <meta charset="utf-8">
    <title>${ param.title }</title>    
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="icon" 
      type="image/png" 
      href="<c:url value="/resources/bootstrap/img/favicon.png" />" >
      
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/anchor-footer.css" />" rel="stylesheet">
    <%--  <link href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />" rel="stylesheet"> --%>
	<link href="<c:url value="/resources/bootstrap/css/framework.css" />" rel="stylesheet">
</head>
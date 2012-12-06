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
	
	<script type="text/javascript" src="http://d3js.org/d3.v2.js"></script>
    <!-- <script type="text/javascript" src="http://mbostock.github.com/d3/d3.csv.js?2.3.0"></script>
    <script type="text/javascript" src="http://mbostock.github.com/d3/d3.layout.js?2.3.0"></script>
    <script type="text/javascript" src="http://mbostock.github.com/d3/d3.time.js?2.3.0"></script> -->
	    <style type="text/css">
	
			svg {
			  font-family: "Helvetica Neue", Helvetica;
			}
			
			.line {
			  fill: none;
			  stroke: #000;
			  stroke-width: 2px;
			}
	
	    </style>		
	
</head>
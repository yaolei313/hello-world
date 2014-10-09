<%-- Set the JSP response header to UTF-8 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>  
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html>
<head>
    <%-- html code --%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>My Site - <decorator:title default="Welcome!" /></title>
	<decorator:head />
</head>
<body>
    <h1>Header8</h1>
    <p><b>Navigation</b></p>
    <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="logo" />
    <hr />
	<decorator:body />
	<hr />
    <h1>Footer</h1>
</body>
</html>
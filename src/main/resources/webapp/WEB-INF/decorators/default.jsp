<%-- Set the JSP response header to UTF-8 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <%-- html code --%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>My Site - <sitemesh:write property='title' /></title>
    <sitemesh:write property='head' />
</head>
<body>
    <h1>Header8</h1>
    <p><b>Navigation</b></p>
    <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="logo" />
    <hr />
    <sitemesh:write property='body' />
	<hr />
    <h1>Footer</h1>
</body>
</html>
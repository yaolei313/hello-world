<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Home Page</title>  
</head>  
<body>  
    <p>Hi <shiro:guest>Guest</shiro:guest>
    <shiro:user>
    <%
    //This should never be done in a normal page and should exist in a proper MVC controller of some sort, but for this
    //tutorial, we'll just pull out Stormpath Account data from Shiro's PrincipalCollection to reference in the
    //<c:out/> tag next:
    request.setAttribute("account", org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));
	%>
<c:out value="${account.givenName}"/></shiro:user>!
    ( <shiro:user><a href="<c:url value="/logout"/>">Log out</a></shiro:user>
    <shiro:guest><a href="<c:url value="/login.jsp"/>">Log in</a></shiro:guest> )
</p>
</body>  
</html>
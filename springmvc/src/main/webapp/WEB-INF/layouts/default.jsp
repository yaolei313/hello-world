<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html>
<head>
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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:set var="root" value="" scope="request" />
<c:set var="img" value="${root}/resources/images" scope="request" />
<c:set var="css" value="${root}/resources/css" scope="request" />
<c:set var="js" value="${root}/resources/jscript" scope="request" />

<html>

<head>
<link rel="stylesheet" href="<c:url value="${css}/pure-css.css" />" />
<link rel="stylesheet" href="<c:url value="${css}/pure-layout.css" />" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Unresolved Error</title>
</head>

<body>
	<div class="error-box">
		<b>Error</b>
	</div>
</body>
</html>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="client"/>
<html lang="${language}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <style>
        <%@include file="../../css/successful.css" %>
    </style>
    <title><fmt:message key="successful.title"/></title>
</head>
<body>
<div class="container">
    <img src="../../images/getStarted.png"/>
    <div class="textContainer"><fmt:message key="successful"/></div>
    <a href="${root}" class="return" align="center" style="text-decoration: none;"><fmt:message
            key="return"/></a>
</div>
</body>
</html>
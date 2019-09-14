<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="logged"/>
<html lang="${language}">
<head>
    <style>
        <%@include file="../../css/logged_in.css" %>
    </style>
    <meta charset="UTF-8">
    <title><fmt:message key="logged.title"/></title>
</head>
<body>
<div class="main">
    <div class="textContainer"><fmt:message key="logged.exit"/></div>
    <a href="logout" class="return" align="center" style="text-decoration: none;"><fmt:message key="logged.yes"/></a>
    <a href="home" class="return" align="center" style="text-decoration: none;"><fmt:message key="logged.no"/></a>
</div>
</body>
</html>
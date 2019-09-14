<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="login"/>
<html lang="${language}">

<head>
    <style>
        <%@include file="../../css/login.css" %>
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><fmt:message key="sign_in"/></title>
</head>

<body>

<div class="main">
    <p class="sign" align="center"><fmt:message key="sign_in"/></p>
    <form class="loginForm" method="post" action="login">
        <label for="uname">
                 <c:choose>
                                    <c:when test="${errors.username!=null}">
                                       <fmt:message key="error.username"/>
                                  </c:when>
                                  <c:when test="${errors.user!=null}">
                                     <fmt:message key="error.exist"/>
                                    </c:when>
                                </c:choose>
        </label>
        <input class="uname" id="uname" required name="username" type="text" align="center" placeholder=<fmt:message
                key="username"/>>
        <label for="pass">
        <c:choose>
              <c:when test="${errors.password!=null}">
               <fmt:message key="error.password"/>
                  </c:when>
           </c:choose>
        </label>
        <input class="pass" id="pass" required name="password" type="password" align="center" placeholder=<fmt:message
                key="password"/>>
        <input type="submit" class="submit" align="center" value=<fmt:message key="sign_in"/>>
        <a href="registration" class="registration" align="center" style="text-decoration: none;"><fmt:message
                key="register"/></a>
        <a href="home" class="return" align="center" style="text-decoration: none;"><fmt:message
                key="return"/></a>
    </form>
</div>
</body>
</html>
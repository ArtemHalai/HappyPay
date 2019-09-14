<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="client"/>
<html lang="${language}">

<head>
    <style>
        <%@include file="../../css/credit_request.css" %>
        <%@include file="../../css/backgroundImage.css" %>
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><fmt:message key="arrears"/></title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="home" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="home"/></a>
    <a href="client_accounts" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="account"/></a>
    <a href="deposit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="deposit"/></a>
    <a href="login" class="w3-bar-item w3-button w3-padding-large w3-hover-white">
    <c:choose>
                    <c:when test="${role!=null}">
                        <fmt:message key="logout"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="login"/>
                    </c:otherwise>
                </c:choose> </a>
  </div>
</div>
<div class="main" style="width:35vw;">
    <p class="sign" align="center"><fmt:message key="arrears_amount"/></p>
<form class="request" method="get" action="pay_arrears">
        <label for="amount">
                 <c:choose>
                                    <c:when test="${errors.amount!=null}">
                                       <fmt:message key="error.amount"/>
                                  </c:when>
                                  <c:when test="${errors.not_enough_amount!=null}">
                                                                         <fmt:message key="error.not_enough_amount"/>
                                                                    </c:when>
                                </c:choose>
        </label>
        <input class="uname" required name="amount" type="number" align="center" placeholder=<fmt:message
                key="amount"/>>

        <input type="submit" class="submit" align="center" value=<fmt:message key="submit"/>>
        <a href="home" class="return" align="center" style="text-decoration: none;"><fmt:message
                key="return"/></a>
    </form>
    </div>
</body>
</html>
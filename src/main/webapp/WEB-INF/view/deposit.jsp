<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="client"/>
<html lang="${language}">

<head>
    <style>
        <%@include file="../../css/index.css" %>
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><fmt:message key="deposit"/></title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="home" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="home"/></a>
    <a href="client_accounts" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="account"/></a>
    <a href="credit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="credit"/></a>
    <a href="login" class="w3-bar-item w3-button w3-padding-large w3-hover-white">
    <c:choose>
                    <c:when test="${role!=null}">
                        <fmt:message key="logout"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="login"/>
                    </c:otherwise>
                </c:choose> </a>
                <img class="w3-bar-item w3-padding-small" style="margin-top:1vh;width:5vw;height:5vh;" src="images/getStarted.png"/>
  </div>
</div>

<div class="w3-green w3-center w3-padding-64">
  <img style="width:20vw;height:25vh;margin-top:4vh;" src="images/getStarted.png"/>
  <p class="text w3-green w3-center w3-padding-16"><fmt:message key="welcome"/></p>

                          <p class="text w3-green w3-center w3-padding-16"><fmt:message key="balance"/>: ${deposit_account.balance}</p>
                          <p class="text w3-green w3-center w3-padding-16"><fmt:message key="currency"/>: ${deposit_account.currency}</p>
                          <p class="text w3-green w3-center"><fmt:message key="term"/>: ${deposit_account.term}</p>
                          <p class="text w3-green w3-center"><fmt:message key="rate"/>: ${deposit_account.rate}</p>
                          <button class="btn"><a href="refill_list_client"><fmt:message key="refill_list"/></></button>

</div>
</body>
</html>
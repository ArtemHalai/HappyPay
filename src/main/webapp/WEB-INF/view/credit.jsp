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
    <title><fmt:message key="credit"/></title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="home" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="home"/></a>
    <a href="client_accounts" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="account"/></a>
    <a href="deposit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="deposit"/></a>
    <a href="limit_request" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="limit_request"/></a>
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
<p class="text w3-green w3-center w3-padding-16"><fmt:message key="limit"/>: ${credit_account.limit}</p>
<p class="text w3-green w3-center w3-padding-16"><fmt:message key="currency"/>: ${credit_account.currency}</p>
<p class="text w3-green w3-center"><fmt:message key="arrears"/>: ${credit_account.arrears}
<c:choose>
                        <c:when test="${credit_account.arrears>0}">
                            <button class="btn"><a href="pay_arrears"><fmt:message key="pay_arrears"/></></button>
                        </c:when>
                    </c:choose>
</p>
<p class="text w3-green w3-center"><fmt:message key="interest_charges"/>: ${credit_account.interestCharges}
<c:choose>
                        <c:when test="${credit_account.interestCharges>0}">
                            <button class="btn"><a href="pay_interest_charges"><fmt:message key="pay_interest"/></></button>
                        </c:when>
                    </c:choose>
</p>
<p class="text w3-green w3-center"><fmt:message key="rate"/>: ${credit_account.rate}</p>
</div>
</body>
</html>
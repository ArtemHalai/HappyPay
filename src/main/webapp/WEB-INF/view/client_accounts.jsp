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
    <title><fmt:message key="account"/></title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="home" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="home"/></a>
    <a href="credit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="credit"/></a>
    <a href="deposit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="deposit"/></a>
    <a href="bill_payment" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="bill_payment"/></a>
        <a href="transfer" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="transfer"/></a>
        <a href="refill" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="refill"/></a>
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
  <img style="width:20vw;height:25vh;margin-top:3vh;" src="images/getStarted.png"/>
  <p class="text w3-green w3-center w3-padding-16"><fmt:message key="welcome"/></p>
  <c:choose>
                          <c:when test="${errors!=null}">
                             <button class="btn"><a href="update_term"><fmt:message key="update_term"/></></button>
                          </c:when>
                          <c:otherwise>
<p class="text w3-green w3-center"><fmt:message key="credit_account"/>:
<c:choose>
                        <c:when test="${client_accounts.credit=='true'}">
                            &#10004;
                        </c:when>
                        <c:otherwise>
                            <button class="btn"><a href="credit_request"><fmt:message key="open_credit"/></></button>
                        </c:otherwise>
                    </c:choose>
</p>
<p class="text w3-green w3-center"><fmt:message key="deposit_account"/>:
<c:choose>
                        <c:when test="${client_accounts.deposit=='true'}">
                            &#10004;
                        </c:when>
                        <c:otherwise>
                            <button class="btn"><a href="open_deposit"><fmt:message key="open_deposit"/></></button>
                        </c:otherwise>
                    </c:choose>
</p>
<p class="text w3-green w3-center"><fmt:message key="balance"/>: ${client_accounts.balance}</p>
<button class="btn text">
  <a href="refill"><fmt:message key="refill"/></a></button>
<p class="text w3-green w3-center"><fmt:message key="validity"/>: ${client_accounts.validity}</p>
<p class="text w3-green w3-center"><fmt:message key="account_number"/>: ${client_accounts.accountNumber}</p>
<button class="btn text"><a href="operation_list_client"><fmt:message key="operation_list"/></></button>
                          </c:otherwise>
                      </c:choose>
</div>
</body>
</html>
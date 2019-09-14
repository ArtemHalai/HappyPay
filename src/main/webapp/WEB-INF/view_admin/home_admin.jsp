<%@page import="java.util.Locale"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="admin"/>
<html lang="${language}">

<head>
    <style>
        <%@include file="../../css/credit_request.css" %>
        <%@include file="../../css/backgroundImage.css" %>
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><fmt:message key="home"/></title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="home_admin" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="home"/></a>
    <a href="credit_request_admin" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="credit_requests"/></a>
    <a href="limit_request_admin" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="limit_requests"/></a>
    <a href="login" class="w3-bar-item w3-button w3-padding-large w3-hover-white">
    <c:choose>
                    <c:when test="${role!=null}">
                        <fmt:message key="logout"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="login"/>
                    </c:otherwise>
                </c:choose> </a>
                <form class="w3-bar-item  w3-padding-large">
                                       <select class="sel" name="locale" onchange="this.form.submit()">
                                       <c:choose>
                                                           <c:when test="${language==null}">
                                                              <% session.setAttribute("language", Locale.getDefault().getLanguage());%>
                                                           </c:when>
                                                       </c:choose>
                                       <option value="en" ${language == 'en' ? "selected" : ""}>English</option>
                                        <option value="ru" ${language == 'ru' ? "selected" : ""}>Русский</option>
                                       <option value="uk" ${language == 'uk' ? "selected" : ""}>Українська</option>
                                        </select>
                                        </form>
  </div>
</div>

<div class="w3-transparent w3-center w3-padding-32">
  <p class="w3-center w3-margin-right" style="font-size:2.5em;font-weight:bold;color:green;"><fmt:message key="welcome"/></p>
  <img style="width:20vw;height:25vh;" src="images/getStarted.png"/>
</div>
</body>
</html>
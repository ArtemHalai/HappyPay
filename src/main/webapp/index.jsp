<%@page import="java.util.Locale"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="nav" />
<html lang="${language}">

<head>
   <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style><%@include file="css/index.css"%></style>
    <title><fmt:message key="nav.home"/></title>
</head>


<body>

<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-green w3-card w3-left-align w3-large">
    <a href="#home" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="nav.home"/></a>
    <a href="#credit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="nav.credit"/></a>
    <a href="#deposit" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="nav.deposit"/></a>
    <a href="#about" class="w3-bar-item w3-button w3-padding-large w3-hover-white"><fmt:message key="nav.label.about"/></a>
    <a href="login" class="w3-bar-item w3-button w3-padding-large w3-hover-white">
    <c:choose>
                    <c:when test="${role!=null}">
                        <fmt:message key="nav.logout"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="nav.login"/>
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

<!-- Header -->
<header id="home" class="w3-container w3-green w3-center" style="padding:128px 16px">
  <img style="width:20vw;height:25vh;" src="images/getStarted.png"/>
  <button class="w3-button r_btn w3-white w3-padding-large w3-large w3-margin-top">
  <a href="client_accounts" class="w3-bar-item w3-button w3-padding-large"><fmt:message key="nav.getstarted"/></a></button>
</header>

<!-- First Grid -->
<div id="credit"class="w3-row-padding w3-padding-64 w3-container">
  <div class="w3-content">
    <div class="w3-twothird">
      <h1><fmt:message key="nav.credit"/></h1>
      <h5 class="w3-padding-32"><fmt:message key="nav.credit.description"/></h5>
    </div>
<img src="images/creditSmile.png"/>
  </div>
</div>

<!-- Second Grid -->
<div id="deposit" class="w3-row-padding w3-padding-64 w3-container">
  <div class="w3-content">
    <div class="w3-twothird">
      <h1><fmt:message key="nav.deposit"/></h1>
      <h5 class="w3-padding-32"><fmt:message key="nav.deposit.description"/></h5>
    </div>
    <img src="images/depositSmile.jpg"/>
  </div>
</div>

<div id="about" class="w3-container w3-green w3-center w3-padding-64">
    <h1><fmt:message key="nav.about"/></h1>
    <h1 class="w3-margin w3-xlarge"><fmt:message key="nav.contact"/></h1>
    <h1 class="w3-margin w3-xlarge"><fmt:message key="nav.mail"/></h1>
</div>
</body>
</html>

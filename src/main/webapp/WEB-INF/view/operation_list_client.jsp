<%@page import="java.util.Locale"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="refill" />
<html lang="${language}">

<head>
   <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style><%@include file="../../css/index.css"%></style>
    <title><fmt:message key="operation_list"/></title>
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
                    <img class="w3-bar-item w3-padding-small" style="margin-top:1vh;width:5vw;height:5vh;" src="images/getStarted.png"/>
  </div>
</div>
<div class="w3-container w3-padding-16" style="margin-top:10vh;">
<table class="w3-table-all w3-hoverable">
            <thead>
            <td><fmt:message key="amount"/></td>
            <td><fmt:message key="operation_date"/></td>
            <td><fmt:message key="operation_type"/></td>
            </thead>
            <c:choose>
                                <c:when test="${errors!=null}">
                                     <tr><fmt:message key="refill_list_error"/></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${operation_list_client}" var="client">
                                                    <tr>
                                                        <td>${client.amount}</td>
                                                        <td>${client.date}</td>
                                                        <td>${client.operationType}</td>
                                                    </tr>
                                                </c:forEach>
                                </c:otherwise>
                            </c:choose>
            <tfoot>
            <td><fmt:message key="amount"/></td>
                        <td><fmt:message key="operation_date"/></td>
                        <td><fmt:message key="operation_type"/></td>
            </tfoot>
</table>
</div>
    <div class="w3-bottom child2 w3-green w3-center w3-padding-32">
            <button class="btn text"><a href="home"><fmt:message key="return"/></></button>
        </div>
</body>
</html>
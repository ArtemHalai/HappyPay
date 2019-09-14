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
<div class="w3-container" style="margin-top:10vh;">
<table class="w3-table-all w3-hoverable">
            <thead>
            <td><fmt:message key="validity"/></td>
            <td><fmt:message key="balance"/></td>
            <td><fmt:message key="amount"/></td>
            <td><fmt:message key="arrears"/></td>
            <td><fmt:message key="decision"/></td>
            </thead>
            <c:choose>
                                <c:when test="${errors!=null}">
                                     <tr><fmt:message key="no_requests"/></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${limit_request_admin}" var="request">
                                                    <tr>
                                                        <td>${request.term}</td>
                                                        <td>${request.balance}</td>
                                                        <td>${request.amount}</td>
                                                        <td>${request.arrears}</td>
                                                        <td style="padding:1%;">
                                                        <c:choose>
                                                                      <c:when test="${request.decision=='false'}">
                                       <a href="limit_request_admin?user_id=${request.userId}&decision=true&amount=${request.amount}" class="return" align="center" style="text-decoration: none;"><fmt:message
                                  key="approve"/></a>
                             <a href="limit_request_admin?user_id=${request.userId}&decision=false&amount=${request.amount}" class="return" align="center" style="text-decoration: none;
                                            background-color:red"><fmt:message
                                            key="cancel"/></a></tr>
                                          </c:when>
                                   </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                </c:otherwise>
                            </c:choose>
            <tfoot>
            <td><fmt:message key="validity"/></td>
                       <td><fmt:message key="balance"/></td>
                       <td><fmt:message key="amount"/></td>
                       <td><fmt:message key="arrears"/></td>
                       <td><fmt:message key="decision"/></td>
            </tfoot>
</table>
</div>
</body>
</html>
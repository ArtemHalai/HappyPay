<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="reg"/>
<html lang="${language}">
<head>
    <style>
        <%@include file="../../css/registration.css" %>
    </style>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <title><fmt:message key="reg"/></title>
</head>
<body>
<div class="main">
    <p class="sign" align="center"><fmt:message key="reg"/></p>
    <form class="registrationForm" action="registration" method="post">
        <label for="name">
           <c:choose>
                         <c:when test="${errors.name!=null}">
                          <fmt:message key="error.name"/>
                             </c:when>
                      </c:choose>
        </label>
        <input class="uname" required id="name" name="name" type="text" align="center" placeholder=<fmt:message
                key="name"/>>
        <label for="surname">
            <c:choose>
                          <c:when test="${errors.surname!=null}">
                           <fmt:message key="error.surname"/>
                              </c:when>
                       </c:choose>
        </label>
        <input class="uname" required id="surname" name="surname" type="text" align="center" placeholder=<fmt:message
                key="surname"/>>
        <label for="username">
           <c:choose>
                <c:when test="${errors.username!=null}">
                <fmt:message key="error.username"/>
                </c:when>
                <c:when test="${errors.user!=null}">
                   <fmt:message key="error.exist"/>
                   </c:when>
                  </c:choose>
        </label>
        <input class="uname" required id="username" name="username" type="text" align="center" placeholder=<fmt:message
                key="username"/>>
        <label for="password">
           <c:choose>
                         <c:when test="${errors.password!=null}">
                          <fmt:message key="error.password"/>
                             </c:when>
                      </c:choose>
        </label>
        <input class="uname" required id="password" name="password" type="password" align="center" placeholder=
        <fmt:message key="password"/>>
        <label for="birthday">
           <c:choose>
                         <c:when test="${errors.birthday!=null}">
                          <fmt:message key="error.birthday"/>
                             </c:when>
                      </c:choose>
        </label>
        <input class="uname birthday" id="birthday" required name="birthday" type="date" align="center" placeholder=<fmt:message
                key="birthday"/>>
        <label for="phoneNumber">
            <c:choose>
                          <c:when test="${errors.phone_number!=null}">
                           <fmt:message key="error.phone_number"/>
                              </c:when>
                       </c:choose>
        </label>
        <input class="uname" required id="phoneNumber" name="phone_number" type="text" align="center" placeholder=
        <fmt:message key="phone_number"/>>
        <input type="submit" class="registration" align="center" value=<fmt:message key="reg"/>>
        <a href="home" class="return" align="center" style="text-decoration: none;"><fmt:message
                key="return"/></a>
    </form>
</div>
</body>
</html>
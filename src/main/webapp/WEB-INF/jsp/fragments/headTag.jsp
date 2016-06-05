<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title><spring:message code="app.title"/></title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <%-- base будет равно http://localhost:8080/topjava
    мы это делаем для того чтобы все урлы рассчитывались относительно контекста приложения, а не сервлета,
    т.е. если мы зашли на topjava/meals и нажали update (<td><a href="meals/update?id=${meal.id}">Update</a></td>),
    мы перешли по ссылке href относительно пути http://localhost:8080/topjava
    --%>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="webjars/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="shortcut icon" href="resources/images/icon-meal.png">
</head>

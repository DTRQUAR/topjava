<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>

<c:forEach var="meal" items="${storeMeals}">
    <c:out value="${meal}"/>
</c:forEach>

</body>
</html>

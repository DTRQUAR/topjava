<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>

<table border>
<c:forEach var="meal" items="${storeMeals}">
    <tr>
        <c:if test="${meal.exceed == true}">
            <td><font color="red">${meal.dateTime}</font></td>
            <td><font color="red">${meal.description}</font></td>
            <td><font color="red">${meal.calories }</font></td>
            <td><font color="red">${meal.exceed}</font></td>
        </c:if>
        <c:if test="${meal.exceed == false}">
            <td><font color="black">${meal.dateTime}</font></td>
            <td><font color="black">${meal.description}</font></td>
            <td><font color="black">${meal.calories }</font></td>
            <td><font color="black">${meal.exceed}</font></td>
        </c:if>
    </tr>
</c:forEach>
</table>

</body>
</html>

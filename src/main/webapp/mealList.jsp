<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>

<table border>
<c:forEach var="meal" items="${mapMeals}">
    <tr>
        <c:if test="${meal.value.exceed == true}">
            <td><font color="red">${meal.value.dateTime}</font></td>
            <td><font color="red">${meal.value.description}</font></td>
            <td><font color="red">${meal.value.calories}</font></td>
            <td><font color="red">${meal.value.exceed}</font></td>
            <td><a href="meals?action=edit&mealID=${meal.key}"/>edit</td>
            <td><a href="meals?action=delete&mealID=${meal.key}"/>delete</td>
        </c:if>
        <c:if test="${meal.value.exceed == false}">
            <td><font color="black">${meal.value.dateTime}</font></td>
            <td><font color="black">${meal.value.description}</font></td>
            <td><font color="black">${meal.value.calories }</font></td>
            <td><font color="black">${meal.value.exceed}</font></td>
            <td><a href="meals?action=edit&mealID=${meal.key}"/>edit</td>
            <td><a href="meals?action=delete&mealID=${meal.key}"/>delete</td>
        </c:if>
    </tr>
    <form method="post"></form>
</c:forEach>
</table>

</body>
</html>

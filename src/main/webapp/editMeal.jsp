<%--
  Created by IntelliJ IDEA.
  User: Qouer
  Date: 12.02.2016
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<form name="test" method="post" action="meals">
    <p><b>Дата и время:</b><a>${mealToEdit.dateTime}</a><br></p>
    <p><b>Описание:</b><input type="text" name="descrition" size="40" value="${mealToEdit.description}"><br></p>
    <p><b>Калории:</b><input type="text" name="calories" size="40" value="${mealToEdit.calories}"><br></p>
    <p><input type="submit" value="Отправить"></p>
    <input type="hidden" name="mealID_1" size="40" value="${mealID_1}">
    <input type="hidden" name="mealID" size="40" value="${mealID}">
</form>
</body>
</html>

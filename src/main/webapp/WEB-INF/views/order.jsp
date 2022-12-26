<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="styles/order.css">
</head>
<body style="background-image: url('/resources/Back.png')">
<c:set var="ListOfSelectedCats" value="${requestScope.ListOfSelectedCats}"/>
<c:set var="user" value="${sessionScope.CurrentUser}"/>
<c:set var="sum" value="${0}"/>
<c:choose>
    <c:when test="${user!=null && ListOfSelectedCats!=null && not empty ListOfSelectedCats}">
        <form method="post">
            <input type="hidden" value="Delete" name="Delete">
            <button class="delete" type="submit">Удалить из корзины</button>
        <c:forEach var="i" items="${ListOfSelectedCats}">
            <div class="cat" >
                <input type="checkbox" name="catsToDelete" value="${i.id}">
                <img class="imageOrder" src="${request.contextPath}/images/cat/${i.id}/${i.id}.png"/>
                <h2 style="vertical-align: middle;display: table-cell;">${i.name}</h2>
                <h4>Владелец:${i.owner}</h4><br>
                <h4>Цвет:${i.color}</h4><br>
                <h4>Возраст:${i.age}</h4><br>
                <h4>Цена:${i.cost}</h4>
                <c:set var="sum" value="${sum=sum+i.cost}"/>
            </div>
        </c:forEach>
        </form>
        <form method="post">
            <div class="check">
                <div class="sum"><h3 style="margin: 0px; text-align: center">СУММА: ${sum}</h3></div>
                <form method="post">
                    <input type="hidden" name="buy" value="buy">
                    <button class="buy" type="submit">Оформить</button>
                </form>
                <button type="button" class="buy" onclick="location.href='/'">Вернуться к магазину</button>
            </div>
        </form>
    </c:when>
    <c:when test="${(user != null && ListOfSelectedCats == null) || (user != null && empty ListOfSelectedCats)}">
        <H2 style="color: black; right: 50%; width: 500px">Вы еще не выбрали ни одного кота</H2>
        <button class="exit" onclick="location.href='/'">Вернуться к магазину</button>
    </c:when>
</c:choose>

</body>
</html>

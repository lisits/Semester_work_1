<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cat</title>
    <link rel="stylesheet" href="styles/cat.css">
</head>
<c:set var="cat" value="${requestScope.Cat}"/>
<body>
<div class="inf">
    <h1>${cat.name}</h1>
    <p>Цвет: ${cat.color} Владелец: ${cat.owner} Стоимость: ${cat.cost}</p>
    <br>
    <img src="${request.contextPath}/images/cat/${cat.id}/${cat.id}.png">
    <br>
    <c:set var="PurchasedCats" value="${requestScope.PurchasedCats}"/>
    <c:set var="isPurchased" value="${false}"/>
    <c:choose>
        <c:when test="${PurchasedCats!=null}">
            <c:forEach var="i" items="${PurchasedCats}">
                <c:choose>
                    <c:when test="${i==cat.id}">
                        <c:set var="isPurchased" value="${true}"/>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${isPurchased==true}">
                    <button class="delete" type="button">В вашей библиотеке</button>
                </c:when>
                <c:when test="${isPurchased==false}">
                    <form method="post" action="/">
                        <input type="hidden" name="SelectedCat" value="${cat.id}">
                        <button class="delete" type="submit">Добавить в корзину</button>
                    </form>
                </c:when>
            </c:choose>
        </c:when>
        <c:when test="${PurchasedCats==null}">
            <form method="post" action="/">
                <input type="hidden" name="SelectedCat" value="${cat.id}">
                <button class="delete" type="submit">Добавить в корзину</button>
            </form>
        </c:when>
    </c:choose>
    <button class="buy" onclick="location.href='/'">В меню</button>
    <br>
    <button class="buy" onclick="location.href='/user'">В личный кабинет</button>
</div>
</body>
</html>

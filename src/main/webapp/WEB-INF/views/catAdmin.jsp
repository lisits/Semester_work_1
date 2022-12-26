<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CatAdmin</title>
    <link rel="stylesheet" href="styles/cat.css">
</head>
<c:set var="cat" value="${requestScope.Cat}"/>
<body>
<div class="inf">
    <form method="post" action="/catChange" class="forma">
        <label>
            Новый котоаватар
            <input type="text" name="newMainImage"/>
        </label>
        <input type="hidden" name="nameOfCat" value="${cat.id}" placeholder="Введите URL">
        <button type="submit">Установить</button>
    </form>

    <form method="post" action="/catChange">
        <input type="hidden" name="id" value="${cat.id}">
        <label>
            Имя:
            <input type="text" name="name" value="${cat.name}"/>
        </label>
        <label>
            Продавец:
            <input type="text" name="owner" value="${cat.owner}"/>
        </label>
        <label>
            Цвет:
            <input type="text" name="color" value="${cat.color}"/>
        </label>
        <label>
            Возраст:
            <input type="text" name="age" value="${cat.age}"/>
        </label>
        <input type="hidden" name="cost" value="${cat.cost}"/>
        <button type="submit">Изменить данные кота</button>
        <br>
    </form>
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
                    <button class="buy" onclick="location.href='/'">В меню</button>
                    <button class="buy" onclick="location.href='/user'">В личный кабинет</button>
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
    <br>

    <img src="${request.contextPath}/images/cat/${cat.id}/${cat.id}.png">
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="/styles/user.css">
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/user.js"></script>
    <link>
</head>
<body>
<div class="container">
<div class="catshopDiv">
    <h3 class="catshopWrite">CATSHOP</h3>
</div>
<c:set var="owner" value="${sessionScope.CurrentUser}"/>
<div class="menu">
    <img class="avatar" src="${request.contextPath}/images/avatar/${owner.id}.png"/>
    <h4>HELLO, ${owner.nick}!</h4>
    <form method="post" action="/userExit">
        <button class="buy" type="submit">Выйти из аккаунта</button>
    </form>
    <button class="buy" onclick="location.href='/'">Вернуться в магазин</button>
    <button id="data1" class="choice">Учетная запись</button>
    <button id="data2" class="choice">Библиотека</button>
    <button id="data3" class="choice">История покупок</button>
</div>
<div>
    <div style="display: none" id="userData1">
        <img style="width: 50px; height: 50px" src="${request.contextPath}/images/avatar/${owner.id}.png"/>
        <form method="post" action="/userChange">
            <label>
                avatar
                <input type="text" name="changeImg" placeholder="введите URL"/>
            </label>
            <input type="hidden" name="id" value="${owner.id}"/>
            <button type="submit">Установить</button>
        </form>

        <form method="post" action="/userChange">
            <input type="hidden" name="id" value="${owner.id}"/>
            <label>
                nick
                <input type="text" name="nick" value="${owner.nick}"/>
            </label>
            <label>
                firstName
                <input type="text" name="first_name" value="${owner.firstName}"/>
            </label>
            <label>
                lastName
                <input type="text" name="last_name" value="${owner.lastName}"/>
            </label>
            <label>
                mail
                <input type="text" name="mail" value="${owner.mail}"/>
            </label>
            <label>
                password
                <input type="text" name="password"/>
            </label>
            <input type="hidden" name="admin" value="${owner.admin}">
            <input type="hidden" name="salt" value="${owner.salt}">
            <button type="submit" name="changeData" value="changeData">Изменить данные</button>
        </form>
        <c:set var="Error" value="${sessionScope.Warning}"/>
        <c:choose>
            <c:when test="${Error!=null}">
                <h3>${Error}</h3>
            </c:when>
        </c:choose>

    </div>
    <c:set var="ListOfAvailableCats" value="${requestScope.AvailableCats}"/>
    <div style="display: none" id="userData2">
        <h3>Ваша библиотека</h3>
        <c:choose>
            <c:when test="${not empty ListOfAvailableCats}">
                <c:forEach var="i" items="${ListOfAvailableCats}">
                    <div class="catWrapper">
                        <form style="margin: 2px" method="get" action="/cat">
                            <input type="hidden" name="ClickCat" value="${i.id}"/>
                            <button class="imageButton" type="submit"><img class="image1"
                                                                           src="${request.contextPath}/images/cat/${i.id}/${i.id}.png">
                            </button>
                        </form>
                        <c:out value="${i.name}"/>
                    </div>
                </c:forEach>
            </c:when>
            <c:when test="${empty ListOfAvailableCats}">
                <h1>Ваша библиотека пуста</h1>
            </c:when>
        </c:choose>

    </div>
    <div style="display: none" id="userData3">
        <h2>Список покупок</h2>
        <c:set var="history" value="${requestScope.history}"/>
        <c:forEach var="h" items="${history}">
            <h3>Заказ номер:${h.key.id} Дата:${h.key.date} Время:${h.key.time} Сумма:${h.key.totalCost}</h3>
            <c:forEach var="j" items="${h.value}">
                <h4>Название:${j.name} Стоимость:${j.cost} <img style="height: 50px; width: auto"
                                                                src="${request.contextPath}/images/cat/${j.id}/${j.id}.png">
                </h4>
            </c:forEach>
        </c:forEach>
    </div>
</div>
</body>
</html>

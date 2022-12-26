<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MainPage</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<div class="container">
    <div class="catshop"><h3 style=" color: white; text-align: right; margin: 5px 10px 0px 0px;">CATSHOP</h3></div>
    <div class="header">
        <button onclick="location.href='/userLogIn'" class="logIn"><h3 style="margin: 0px">Авторизироваться</h3>
        </button>
        <c:choose>
            <c:when test="${sessionScope.CurrentUser!=null}">
                <c:set var="s" value="${sessionScope.CurrentUser}"/>
                <img style="width: 50px; height: 50px" src="${request.contextPath}/images/avatar/${s.id}.png"/>
                <c:out value="Добро пожаловать, ${s.nick}"/>
            </c:when>
        </c:choose>
        <button onclick="location.href='/order'" class="basket"><h3 style="margin: 0px">Корзина</h3></button>
    </div>
    <c:set var="catList" value="${requestScope.catList}"/>
    <c:set var="PurchasedCats" value="${requestScope.PurchasedCats}"/>
    <h2>Все коты</h2>
    <div class="catDiv">
        <c:forEach var="i" items="${catList}">
            <c:set var="purchased" value="${false}"/>
            <c:choose>
                <c:when test="${PurchasedCats!=null}">
                    <div class="catWrapper">
                        <form style="margin: 2px" method="get" action="/cat">
                            <input type="hidden" name="ClickCat" value="${i.id}"/>
                            <button style="border: none;background-color: white" type="submit"><img
                                    class="catImage"
                                    src="${request.contextPath}/images/cat/${i.id}/${i.id}.png">
                            </button>
                        </form>
                        <c:forEach var="j" items="${PurchasedCats}">
                            <c:choose>
                                <c:when test="${i.id==j}">
                                    <c:set var="purchased" value="${true}"/>
                                    <H4 style="margin: 0px">В вашей библиотеке</H4>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${purchased==false}">
                                <form style=" float: left;margin: 2px" method="post">
                                    <input type="hidden" name="SelectedCat" value="${i.id}">
                                    <button class="buttonImage" type="submit">
                                        <img style="height: 50px;width: auto" src="/resources/basket.png">
                                    </button>
                                </form>
                            </c:when>
                        </c:choose>
                        <p>Имя: ${i.name}</p>
                        <p>Владелец: ${i.owner}</p>
                        <p>Цвет: ${i.color}</p>
                        <p>Стоимость: ${i.cost}$</p>
                    </div>
                </c:when>

                <c:when test="${PurchasedCats==null}">
                    <div class="catWrapper">
                        <form style="margin: 2px" method="get" action="/cat">
                            <input type="hidden" name="ClickCat" value="${i.id}"/>
                            <button style="border: none;background-color: white" type="submit"><img
                                    class="catImage"
                                    src="${request.contextPath}/images/cat/${i.id}/${i.id}.png"></button>
                        </form>
                        <form style=" float: left;margin: 2px" method="post">
                            <input type="hidden" name="SelectedCat" value="${i.id}">
                            <button class="buttonImage" type="submit">
                                <img style="height: 50px;width: auto" src="/resources/basket.png"></button>
                        </form>
                        <p>Имя: ${i.name}</p>
                        <p>Владелец: ${i.owner}</p>
                        <p>Цвет: ${i.color}</p>
                        <p>Стоимость: ${i.cost}</p>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
    <div class="filterDiv">
        <form method="get">
            <h3>Порядок</h3>
            Упорядочить по:
            <select name="columnCatForOrder">
                <option value="name">name</option>
                <option value="owner">owner</option>
                <option value="color">color</option>
                <option value="age">age</option>
                <option value="cost">cost</option>
            </select><br>
            В порядке:
            <select name="descOrAsc">
                <option value="asc">asc</option>
                <option value="desc">desc</option>
            </select>
            <br>
            <h3>Фильтр</h3>
            Отобрать по:
            <select name="columnCatForFilter">
                <option value="name">name</option>
                <option value="owner">owner</option>
                <option value="color">color</option>
            </select><br>
            Значение:
            <input type="text" name="filterArgument" placeholder="Укажите пусто чтобы отключить фильтр">
            <br>
            <button class="filter" type="submit" name="Filter" value="Filter">Включить фильтр</button>
        </form>
    </div>
</div>
</body>
</html>

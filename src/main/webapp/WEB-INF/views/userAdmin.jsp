<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserAdmin</title>
    <link rel="stylesheet" href="/styles/user.css">
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/userAdmin.js"></script>
</head>
<body>
<div class="container">
    <div class="catshopDiv"><h3 class="catshopWrite">CATSHOP</h3></div>
    <c:set var="owner" value="${sessionScope.CurrentUser}"/>
    <div class="menu">
        <img class="avatar" src="${request.contextPath}/images/avatar/${owner.id}.png"/>
        <h4>HELLO, ${owner.nick}!</h4>
        <form method="post" action="/userExit">
            <button class="buy" type="submit">Выйти из аккаунта</button>
        </form>
        <button class="buy" class="buy" onclick="location.href='/'">Вернуться в магазин</button>
        <button id="data1" class="choice">Учетная запись</button>
        <button id="data2" class="choice">Библиотека</button>
        <button id="data3" class="choice">Коты*</button>
        <button id="data4" class="choice">Пользователи*</button>
        <button id="data5" class="choice">История покупок</button>
    </div>
    <div>
        <div style="display: none" id="userData1">
            <img style="height: 50px; width: 50px" src="${request.contextPath}/images/avatar/${owner.id}.png"/>
            <form method="post" action="/userChange">
                <label>
                    avatar
                    <input type="text" name="changeImg" placeholder="Введите URL"/>
                </label>
                <input type="hidden" name="id" value="${owner.id}"/>
                <button type="submit">Установить</button>
            </form>

            <form method="post" action="/userChange">
                <input type="hidden" name="id" value="${owner.id}"/>
                <label>
                    nick
                    <input type="text" name="nick" value="${owner.nick}"/>
                </label><br>
                <label>
                    firstName
                    <input type="text" name="first_name" value="${owner.firstName}"/>
                </label><br>
                <label>
                    lastName
                    <input type="text" name="last_name" value="${owner.lastName}"/>
                </label><br>
                <label>
                    mail
                    <input type="text" name="mail" value="${owner.mail}"/>
                </label><br>
                <label>
                    password
                    <input type="text" name="password"/>
                </label><br>
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
                    <h1>У вас еще нет котов</h1>
                </c:when>
            </c:choose>

        </div>
        <div style="display: none" id="userData3">
            <h2>Коты</h2>
            <form method="post" action="/createCat">
                <label>
                    name
                    <input type="text" name="name">
                </label>
                <br>
                <label>
                    owner
                    <input type="text" name="owner">
                </label>
                <br>
                <label>
                    color
                    <input type="text" name="color">
                </label>
                <br>
                <label>
                    age
                    <input type="number" name="age">
                </label>
                <br>
                <label>
                    cost
                    <input type="number" name="cost">
                </label>
                <br>
                <button type="submit">Добавить нового кота</button>
            </form>

            <form method="post">
                <button type="submit" name="deleteCat" value="delete">Удалить кота</button>
                <table>
                    <tr>
                        <th>id</th>
                        <th>name</th>
                        <th>owner</th>
                        <th>color</th>
                        <th>age</th>
                        <th>cost</th>
                    </tr>
                    <c:forEach var="i" items="${CatList}">
                        <tr>
                            <td>${i.id}</td>
                            <td>${i.name}</td>
                            <td>${i.owner}</td>
                            <td>${i.color}</td>
                            <td>${i.age}</td>
                            <td>${i.cost}</td>
                            <td><input type="checkbox" name="cats" value="${i.id}"></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
        <div style="display: none" id="userData4">
            <H2>Пользователи</H2>
            <c:set var="ListOfUsers" value="${requestScope.ListOfUsers}"/>
            <form method="get">
                Упорядочить по:
                <select name="columnCatForOrder">
                    <option value="id">id</option>
                    <option value="nick">nick</option>
                    <option value="first_name">first_name</option>
                    <option value="last_name">last_name</option>
                    <option value="mail">mail</option>
                    <option value="password">password</option>
                    <option value="admin">admin</option>
                </select>
                В порядке:
                <select name="descOrAsc">
                    <option value="asc">asc</option>
                    <option value="desc">desc</option>
                </select>

                Отобрать по:
                <select name="columnCatForFilter">
                    <option value="id">id</option>
                    <option value="nick">nick</option>
                    <option value="first_name">first_name</option>
                    <option value="last_name">last_name</option>
                    <option value="mail">mail</option>
                    <option value="admin">admin</option>
                </select>
                Значение:
                <input type="text" name="filterArgument" placeholder="Укажите пусто чтобы отключить фильтр">
                <button type="submit" name="FilterUser" value="FilterUser">Включить фильтр</button>
            </form>

            <form method="post">
                <button type="submit" name="delete" value="delete">Удалить</button>
                <button type="submit" name="signAsAdmin" value="signAsAdmin">Сделать админом</button>
                <button type="submit" name="writeOffAsAdmin" value="writeOffAsAdmin">Убрать админа</button>
                <table>
                    <tr>
                        <th>id</th>
                        <th>Nick</th>
                        <th>FirstName</th>
                        <th>LastName</th>
                        <th>Mail</th>
                        <th>Admin</th>
                    </tr>
                    <c:forEach var="i" items="${ListOfUsers}">
                        <tr>
                            <td>${i.id}</td>
                            <td>${i.nick}</td>
                            <td>${i.firstName}</td>
                            <td>${i.lastName}</td>
                            <td>${i.mail}</td>
                            <td>${i.admin}</td>
                            <td><input type="checkbox" name="owner" value="${i.id}"></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>

        <div style="display: none" id="userData5">
            <h2>Список покупок</h2>
            <c:set var="history" value="${requestScope.history}"/>
            <c:forEach var="h" items="${history}">
                <h3>Заказ номер: ${h.key.id} Дата:${h.key.date} Время:${h.key.time} Сумма:${h.key.totalCost}</h3>
                <c:forEach var="j" items="${h.value}">
                    <h4>Название:${j.name} Стоимость:${j.cost} <img style="height: 50px; width: auto"
                                                                    src="${request.contextPath}/images/cat/${j.id}/${j.id}.png">
                    </h4>
                </c:forEach>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

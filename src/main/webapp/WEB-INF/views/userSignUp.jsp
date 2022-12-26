<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
    <link rel="stylesheet" href="styles/UserSignUp.css">
</head>
<body style="background-image: url('/resources/Back.png')">

<div class="parent">
<div class="center-div">
    <div class="catDiv">
        <h2 class="catshop"> CATSHOP </h2>
        </div>
    <div class="cat"><h2 class="catInscription">Cat</h2></div>

    <form method="post">
        <div class="field">
        <label>
            nick<br>
            <input type="text" name="nick"/>
        </label>
        </div>
        <div class="field">
        <label>
            Имя<br>
            <input type="text" name="first_name"/>
        </label>
        </div>
        <div class="field">
        <label>
            Фамилия<br>
            <input type="text" name="last_name"/>
        </label>
        </div>
        <div class="field">
        <label>
            mail<br>
            <input type="text" name="mail"/>
        </label>
        </div>
        <div class="field">
        <label>
            Пароль<br>
            <input type="text" name="password"/>
        </label>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>
    <button style="margin-bottom: 0px; align-content: center" onclick="location.href='/userLogIn'">Вернуться</button>
    <c:set var="Error" value="${sessionScope.Warning}"/>
    <c:choose>
        <c:when test="${Error!=null}">
            <h4 class="wrong">${Error}</h4>
        </c:when>
    </c:choose>
</div>
</div>
</body>
</html>

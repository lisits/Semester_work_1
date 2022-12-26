<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignIn</title>
    <link rel="stylesheet" href="styles/UserSignUp.css">
</head>
<body style="background-image: url('/resources/Back.png')">
<div class="parent">
    <div class="center-div">
        <div class="catDiv"></div>
        <div class="cat"><h2 class="catInscription">CATSHOP</h2></div>
        <form method="post">
            <div class="field">
                <label>
                    mail<br>
                    <input type="text" name="LogInmail"/>
                </label>
            </div>
            <div class="field">
                <label>
                    password<br>
                    <input type="text" name="LogInpassword"/>
                </label>
            </div>
            <button type="submit">Войти</button>
        </form>
        <button style="margin-bottom: 10px" onclick="location.href='/userSignIn'">Зарегистрироваться</button>
        <button onclick="location.href='/'">Вернуться в меню</button>
        <c:choose>
            <c:when test="${sessionScope.NotFound!=null}">
                <h4 class="wrong">${sessionScope.NotFound}</h4>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>

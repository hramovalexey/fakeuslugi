<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="fakeuslugi">
<head>
    <title>FakeUslugi</title>
    <base href="/">
</head>
<body ng-controller="mainController">

<h1>HIIIIIIII</h1>
<h2>{{thisUser}}</h2>
<button ng-click="goLogin()">авторизация</button>
<button ng-click="goRegister()">регистрация</button>
<button ng-click="doLogOut">выход</button>
<ng-view></ng-view>

<%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>--%>
<script>
    <%@include file="js/lib/angular.min.js"%>
    <%@include file="js/lib/angular-resource.js"%>
    <%@include file="js/lib/angular-route.min.js"%>
    <%@include file="js/app.js"%>
    <%@include file="js/mainController.js"%>
    <%@include file="js/orderListController.js"%>
    <%@include file="js/authController.js"%>
</script>
</body>
</html>

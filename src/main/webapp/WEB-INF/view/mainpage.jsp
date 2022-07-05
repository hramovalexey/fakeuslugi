<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="fakeuslugi">
<head>
    <title>FakeUslugi</title>
    <base href="/">
</head>
<style>
    table, td, th {
        border: 1px solid black;
    }

</style>
<body ng-controller="mainController">

<h1>Fakeuslugi</h1>
<h2>{{thisUser}}</h2>
<ng-view></ng-view>

<%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>--%>
<script>
    <%@include file="js/lib/angular.min.js"%>
    <%@include file="js/lib/angular-resource.js"%>
    <%@include file="js/lib/angular-route.min.js"%>
    <%@include file="js/app.js"%>
    <%@include file="js/mainController.js"%>
    <%@include file="js/orderListController.js"%>
    <%@include file="js/orderController.js"%>
    <%@include file="js/historyController.js"%>
    <%@include file="js/authController.js"%>
</script>
</body>
</html>

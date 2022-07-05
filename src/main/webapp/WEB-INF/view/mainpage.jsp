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

    table {
        margin: 10px;
        margin-left: 0;
    }

    div {
        margin: 10px;
        margin-left: 0;
    }

    button {
        display: block;
        margin: 10px;
        margin-left: 0;
    }

    input {
        display: block;
    }

    .content{
        width: max-content;
        margin-left: auto;
        margin-right: auto;
    }

    .status {
        font-weight: bold;
        color: blue;
    }

</style>
<body class="content" ng-controller="mainController">

<h1>Fakeuslugi</h1>
<h2>{{thisUser}}</h2>
<hr>
<ng-view></ng-view>

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

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order list</title>
</head>
<body>
<button ng-show="!isLoggedIn()" ng-click="goLogin()">авторизация</button>
<button ng-show="!isLoggedIn()" ng-click="goRegister()">регистрация</button>
<button ng-show="isLoggedIn()" ng-click="doLogOut()">выход</button>

<h1>Список оказанных услуг</h1>
<div ng-show="currentOrderList && currentOrderList.orders.length != 0">
    <table>
        <tr>
            <th>Дата, время подачи заявки</th>
            <th>Номер заявки</th>
            <th>Название услуги</th>
        </tr>
        <tr ng-repeat="order in currentOrderList.orders" ng-click="onListClick(order.id)" style="cursor: pointer;">
            <td>{{getDateString(order.date)}}</td>
            <td>{{order.id}}</td>
            <td>{{order.name}}</td>
        </tr>
    </table>

</div>
<div class="status" ng-show="currentOrderList != null && currentOrderList.orders.length == 0">
    Оказанных услуг нет
</div>

<button ng-show="isLoggedIn()" ng-click="goOrder()">Заказать услугу</button>

</body>
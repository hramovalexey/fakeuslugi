<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auth</title>
</head>
<body>


<div ng-show="authMode=='register'">
    <h4>Форма регистрации</h4>

    <div>
        <label for="secondName">Фамилия*</label>
        <input type="text" id="secondName" ng-model="newCustomer.secondName"/>

        <label for="name">Имя*</label>
        <input type="text" id="name" ng-model="newCustomer.name"/>


        <label for="thirdName">Отчество</label>
        <input type="text" id="thirdName" ng-model="newCustomer.thirdName"/>

        <label for="email">Email*</label>
        <input type="text" id="email" ng-model="newCustomer.email"/>

        <label for="password">Пароль*</label>
        <input type="text" id="password" ng-model="newCustomer.password"/>
    </div>
    <button ng-click="doRegister()">Ok</button>
    <button ng-click="authMode='login'">авторизоваться</button>
    <div class="status">{{statusRegister}}</div>
</div>

<div ng-show="authMode=='login'">
    <h4>Форма авторизации</h4>

    <div>
        <label for="emailLog">Email</label>
        <input type="text" id="emailLog" ng-model="loginCustomer.email"/>

        <label for="passwordLog">Пароль</label>
        <input type="text" id="passwordLog" ng-model="loginCustomer.password"/></div>
    <button ng-click="doLogin()">Ok</button>
    <button ng-click="authMode='register'">зарегистрироваться</button>
    <div class="status">{{statusLogin}}</div>
</div>
<button ng-click="goOrderList()">К списку оказанных услуг</button>
</body>
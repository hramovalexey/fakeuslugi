<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order</title>
</head>
<body>

<h1>Форма оказания услуги</h1>
<div>Выбрать услугу: </div>
<select ng-model="inputService" ng-options="service.name for service in getServiceList()" ng-change="onSelectChange()"></select>
<table ng-show="inputService">
    <tr>
        <th>Наименование услуги</th>
        <th>Общий лимит по услуге</th>
        <th>Оставшийся лимит по услуге</th>
    </tr>
    <tr>
        <td>{{inputService.name}}</td>
        <td>{{inputService.totalLimit}}</td>
        <td>{{inputService.currentLimit}}</td>
    </tr>

</table>
<h3>Информация о заявителе:</h3>
<div>Фамилия: <span>{{currentUserObj.secondName}}</span></div>
<div>Имя: <span>{{currentUserObj.name}}</span></div>
<div>Отчество: <span>{{currentUserObj.thirdName}}</span></div>
<div>Email: <span>{{currentUserObj.email}}</span></div>
<div></div>
<div></div>

<div ng-show="inputService && inputService.currentLimit > 0">
<div>Добавить комментарий к услуге:</div>
<input ng-model="inputComment" type="text"/>
</div>

<div>{{statusOrder}}</div>
<div ng-show="!isSuccessSent">
    <button ng-click="doOrder()" ng-disabled="!inputService || inputService.currentLimit <= 0" ng-click="">Отправить</button>
    <button ng-click="goOrderList()">Отмена</button>
</div>
<div ng-show="isSuccessSent">
    <button ng-click="goHistory(successId)">В карточку услуги</button>
    <button ng-click="goOrderList()">К списку оказанных услуг</button>
</div>
</body>
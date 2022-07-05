<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>History</title>
</head>
<body>

<h1>Карточка оказанной услуги</h1>
<div>Фамилия: <span>{{currentUserObj.secondName}}</span></div>
<div>Имя: <span>{{currentUserObj.name}}</span></div>
<div>Отчество: <span>{{currentUserObj.thirdName}}</span></div>
<div>Email: <span>{{currentUserObj.email}}</span></div>
<div>Название услуги: <span>{{currentOrder.name}}</span></div>
<div>Номер заявки: <span>{{currentOrder.id}}</span></div>
<div>Комментарий заявителя: <span>{{currentOrder.userComment}}</span></div>
<h3>История оказания услуги</h3>
<table>
    <tr>
        <th>Дата, время</th>
        <th>Статус</th>
        <th>Комментарий исполнителя</th>
    </tr>
    <tr ng-repeat="status in currentOrder.statusHistory">
        <td>{{getDateString(status.date)}}</td>
        <td>{{status.statusName}}</td>
        <td>{{status.executorComment}}</td>
    </tr>
</table>

<button ng-click="goOrderList()">К списку оказанных услуг</button>
</body>
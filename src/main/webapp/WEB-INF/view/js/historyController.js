fakeuslugi.controller("historyController", function ($scope, $routeParams, $location) {
    console.log("history controller started");

    console.log("id = " + $routeParams.id);
    let inputId = $routeParams.id;

    $scope.currentUserObj = currentUserObj;
    let currentOrderObj = orderListContr.currentOrderList;

    $scope.currentOrder = mainContr.findByField(currentOrderObj.orders, "id", inputId);

    $scope.getDateString = function (mills) {
        return mainContr.getDateString(mills);
    }

    $scope.goOrderList = function () {
        $location.path('/');
    }

});
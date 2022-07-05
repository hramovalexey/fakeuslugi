fakeuslugi.controller("orderController", function ($scope, $resource, $location) {
    console.log("order controller started");

    let LIMIT_EXCEEDED = '\u0412\u044b\u0020\u043d\u0435\u0020\u043c\u043e\u0436\u0435\u0442\u0435\u0020\u0437\u0430\u043a\u0430\u0437\u0430\u0442\u044c\u0020\u0434\u0430\u043d\u043d\u0443\u044e\u0020\u0443\u0441\u043b\u0443\u0433\u0443\u002c\u0020\u0442\u0430\u043a\u0020\u043a\u0430\u043a\u0020\u043f\u043e\u0020\u043d\u0435\u0439\u0020\u043f\u0440\u0435\u0432\u044b\u0448\u0435\u043d\u0020\u043b\u0438\u043c\u0438\u0442';
    let DEFAULT_SUCCESS_ID = '\u0423\u0441\u043b\u0443\u0433\u0430\u0020\u0443\u0441\u043f\u0435\u0448\u043d\u043e\u0020\u043e\u043a\u0430\u0437\u0430\u043d\u0430\u002e\u0020\u041d\u043e\u043c\u0435\u0440\u0020\u0432\u0430\u0448\u0435\u0439\u0020\u0437\u0430\u044f\u0432\u043a\u0438\u003a';
    let DEFAULT_SUCCESS_SENT = '\u041f\u043e\u0434\u0440\u043e\u0431\u043d\u043e\u0441\u0442\u0438\u0020\u0432\u044b\u0441\u043b\u0430\u043d\u044b\u0020\u043d\u0430\u0020\u0432\u0430\u0448\u0443\u0020\u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u0443\u044e\u0020\u043f\u043e\u0447\u0442\u0443';
    let UNKNOWN_ERROR = '\u041f\u0440\u0438\u0020\u0437\u0430\u043a\u0430\u0437\u0435\u0020\u0443\u0441\u043b\u0443\u0433\u0438\u0020\u043f\u0440\u043e\u0438\u0437\u043e\u0448\u043b\u0430\u0020\u043d\u0435\u043f\u0440\u0435\u0434\u0432\u0438\u0434\u0435\u043d\u043d\u0430\u044f\u0020\u043e\u0448\u0438\u0431\u043a\u0430';

    $scope.currentUserObj = currentUserObj;

    $scope.inputService = null;
    $scope.inputComment = "";

    $scope.statusOrder = "";

    // If form was successfully submitted then this variable must be set to true
    $scope.isSuccessSent = false;
    $scope.successId = "";

    $scope.goOrderList = function () {
        $location.path('/');
    }

    $scope.goHistory = function (id) {
        $location.path('/history').search('id', id);
    }

    $scope.getServiceList = function () {
        return mainContr.currentServiceList;
    }

    $scope.onSelectChange = function () {
        console.log("selected service  " + JSON.stringify($scope.inputService));
        if ($scope.inputService.currentLimit <= 0) {
            $scope.statusOrder = LIMIT_EXCEEDED;
        } else {
            $scope.statusOrder = "";
        }
    }

    $scope.doOrder = function () {
        $scope.statusOrder = "";
        if ($scope.inputService) {
            let url = BASE_URL + "service/order/";
            let newOrder =
                {
                    serviceId: $scope.inputService.id,
                    userComment: $scope.inputComment
                };
            let NewOrder = $resource(url, {}, mainContr.actions);
            NewOrder.post(newOrder).$promise.then(function (response) {
                    console.log("response = " + JSON.stringify(response));
                    if (response.status == 200) {
                        $scope.isSuccessSent = true;
                        $scope.successId = JSON.parse(response.data).id;
                        $scope.statusOrder = DEFAULT_SUCCESS_ID + ' ' + $scope.successId + ' . ' + DEFAULT_SUCCESS_SENT;
                        orderListContr.getOrderList(); // refresh order list
                    } else {
                        $scope.statusOrder = UNKNOWN_ERROR;
                    }
                },
                function (error) {
                    $scope.statusOrder = mainContr.tryTransformJsonErrorMessageToString(error);
                }
            );
        }
    }

});
fakeuslugi.controller("orderListController", function ($scope, $resource, $location) {
    console.log("orderList controller started");

    orderListContr = $scope;

    mainContr.checkCurrentUser();

    $scope.currentOrderList = null;
    $scope.statusGetOrderList = "";

    $scope.getOrderList = function () {
        $scope.statusGetOrderList = ""
        if (!$scope.isLoggedIn()) {
            $scope.currentOrderList = null;
            return;
        }
        let url = BASE_URL + "service/orders/";
        let OrderList = $resource(url, {}, mainContr.actions);
        OrderList.get().$promise.then(
            function (response) {
                if (response.status == 200) {
                    $scope.currentOrderList = (JSON.parse(response.data));
                }
            },
            function(error) {
                console.error(error);
                $scope.statusGetOrderList = mainContr.tryTransformJsonErrorMessageToString(error);
            }
        );
    }

    $scope.goOrder = function () {
        mainContr.getServiceList();
        $location.path('order');
    }

    $scope.getDateString = function (mills) {
        return mainContr.getDateString(mills);
    }

    $scope.isLoggedIn = function () {
        return mainContr.isLoggedIn();
    }

    $scope.goLogin = function () {
        $location.path('auth').search('authmode', 'login');
    }

    $scope.goRegister = function () {
        $location.path('auth').search('authmode', 'register');
    }

    $scope.onListClick = function (id) {
        console.log(id);
        $location.path('/history').search('id', id);
    }

    $scope.getOrderList();



});
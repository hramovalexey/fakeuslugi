fakeuslugi.controller("authController", function ($scope, $routeParams, $location, $resource) {

    console.log("authController started");
    console.log("route params = " + $routeParams.authmode);

    let INCORRECT_CREDENTIALS = '\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0435\u0020\u0443\u0447\u0435\u0442\u043d\u044b\u0435\u0020\u0434\u0430\u043d\u043d\u044b\u0435';
    let INCORRECT_FIELDS_CONTENT = '\u041d\u0430\u0434\u043e\u0020\u0437\u0430\u043f\u043e\u043b\u043d\u0438\u0442\u044c\u0020\u0432\u0441\u0435\u0020\u043e\u0431\u044f\u0437\u0430\u0442\u0435\u043b\u044c\u043d\u044b\u0435\u0020\u043f\u043e\u043b\u044f';
    let SUCCESS_REGISTER = '\u0412\u044b\u0020\u0443\u0441\u043f\u0435\u0448\u043d\u043e\u0020\u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043b\u0438\u0441\u044c\u002e\u0020\u0412\u0432\u0435\u0434\u0438\u0442\u0435\u0020\u0441\u0432\u043e\u0438\u0020\u0443\u0447\u0435\u0442\u043d\u044b\u0435\u0020\u0434\u0430\u043d\u043d\u044b\u0435\u0020\u0432\u0020\u0444\u043e\u0440\u043c\u0435\u0020\u0430\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u0438';

    $scope.newCustomer = {};
    $scope.loginCustomer = {};

    $scope.authMode = $routeParams.authmode;
    $scope.statusRegister = "";
    $scope.statusLogin = "";



    $scope.doRegister = function () {
        $scope.statusRegister = "";
        if ($scope.newCustomer.secondName && $scope.newCustomer.name && $scope.newCustomer.password
            && $scope.newCustomer.email && $scope.newCustomer.password) {
            let url = BASE_URL + "auth/register/";
            let newUserCreds = {
                secondName: $scope.newCustomer.secondName,
                name: $scope.newCustomer.name,
                thirdName: $scope.newCustomer.thirdName,
                email: $scope.newCustomer.email,
                password: $scope.newCustomer.password
            };
            let NewUser = $resource(url, {}, mainContr.actions);
            NewUser.post(newUserCreds).$promise.then(
                function (response) {
                    if (response.status == 200) {
                        $scope.statusRegister = SUCCESS_REGISTER;
                    }
                }, function (error) {

                    $scope.statusRegister = mainContr.tryTransformJsonErrorMessageToString(error);
                }
            );
        } else {
            $scope.statusRegister = INCORRECT_FIELDS_CONTENT;
        }
    }

    $scope.doLogin = function () {
        $scope.statusLogin = "";
        if ($scope.loginCustomer.email && $scope.loginCustomer.password) {
            let url = BASE_URL + "auth/login/";
            let authUserCreds =
                {
                    email: $scope.loginCustomer.email,
                    password: $scope.loginCustomer.password,
                };
            let AuthUser = $resource(url, {}, mainContr.actions);
            AuthUser.post(authUserCreds).$promise.then(function (response) {
                    console.log("response = " + JSON.stringify(response));
                    let jwt = response.headers().authorization;
                    if (response.status == 200 && jwt) {
                        mainContr.setJwt(jwt);
                        mainContr.setCurrentUser(JSON.parse(response.data));
                        $location.path('/');
                    } else {
                        $scope.statusLogin = INCORRECT_CREDENTIALS;
                    }
                },
                function (error) {
                    $scope.statusLogin = INCORRECT_CREDENTIALS;
                }
            );
        } else {
            $scope.statusLogin = INCORRECT_FIELDS_CONTENT;
        }
    }

    $scope.doLogOut = function () {
        mainContr.doLogOut();
    }

    $scope.goOrderList = function () {
        $location.path('/');
    }
});
fakeuslugi.controller("mainController", function ($scope, $rootScope, $resource, $window, $location) {
    mainContr = $scope;
    BASE_URL = "http://" + $location.host() + ":" + $location.port() + "/";
    let WELLCOME_MESSAGE = '\u0414\u043e\u0431\u0440\u043e\u0020\u043f\u043e\u0436\u0430\u043b\u043e\u0432\u0430\u0442\u044c\u002c';
    let DEFAULT_NO_USER_MESSAGE = '\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0443\u0439\u0442\u0435\u0441\u044c\u0020\u0438\u043b\u0438\u0020\u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c';
    $scope.thisUser = DEFAULT_NO_USER_MESSAGE;
    $scope.currentUserObj = {};

    /*$scope.setUserNameByJwt = function () {
        let name = getUserNameByJwt();
        if (!name) {
            clearJwt();
        }
        $scope.thisUser = name;
    }*/

    /*let getUserNameByJwt = function () {
        let jwt = $window.localStorage.getItem("fakeuslugi");
        if (!jwt) {
            return "";
        }
        let arr = jwt.split('.');
        if (arr.length < 2) {
            return "";
        }
        try {
            let parsed = atob(arr[1]);
            let json = {};
            json = JSON.parse(parsed);
            if (json.hasOwnProperty('sub')) {
                return json.sub;
            }
        } catch (e) {
            return "";
        }
        return "";
    }*/

    $scope.actions = {
        post: {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': function () {
                    return getJwtHeader();
                }
            },
            hasBody: true,
            transformResponse: function (data, headers, status) {
                let response = {};
                response = {
                    status: status,
                    data: data,
                    headers: headers
                };
                return response;
            }
        },
        get: {
            method: 'get',
            headers: {
                'Accept': 'application/hal+json',
                'Authorization': function () {
                    return getJwtHeader();
                }
            },
            transformResponse: function (data, headers, status) {
                let response = {};
                response = {
                    status: status,
                    data: data,
                    headers: headers,
                    // data: data
                };
                return response;
            }
        }
    };

    $scope.checkCurrentUser = function () {
        if (!hasJwt()) {
            $scope.setCurrentUser(null);
        }
        let url = BASE_URL + "auth/testuser/";
        let CheckUser = $resource(url, {}, $scope.actions);
        CheckUser.get().$promise.then(
            function(response) {
            if (response.status == 200) {
                $scope.setCurrentUser(JSON.parse(response.data));
                return;
            }
            $scope.setCurrentUser(null);
        }, function(error) {
                $scope.setCurrentUser(null);
            }
        );
    }



    $scope.setCurrentUser = function(userObj) {
        if (userObj
            && userObj.hasOwnProperty("secondName")
            && userObj.hasOwnProperty("name")
            && userObj.hasOwnProperty("thirdName")
            && userObj.hasOwnProperty("email")) {
            $scope.currentUserObj = userObj;
            $scope.thisUser = WELLCOME_MESSAGE + ' ' + userObj.name + ' ' + userObj.secondName;
        } else {
            $scope.thisUser = DEFAULT_NO_USER_MESSAGE;
        }
    }

    let getJwtHeader = function () {
        let jwt = $window.localStorage.getItem("fakeuslugi");
        return jwt ? "Bearer " + jwt : "";
    }

    $scope.setJwt = function (token) {
        $window.localStorage.setItem("fakeuslugi", token);
    }

    $scope.clearJwt = function () {
        $window.localStorage.removeItem("fakeuslugi");
    }



    let hasJwt = function () {
        return $window.localStorage.getItem("fakeuslugi") != null;
    }

    $scope.doLogOut = function () {
        $scope.clearJwt();
        $scope.setCurrentUser(null);
    }


    $scope.goLogin = function () {
        $location.path('auth').search('authmode', 'login');
    }

    $scope.goRegister = function () {
        $location.path('auth').search('authmode', 'register');
    }

    $scope.checkCurrentUser();

});
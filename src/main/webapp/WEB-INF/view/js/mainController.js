currentUserObj = null;

fakeuslugi.controller("mainController", function ($scope, $rootScope, $resource, $window, $location) {
    console.log("main controller started");
    mainContr = $scope;
    // BASE_URL = "http://" + $location.host() + ":" + $location.port() + "/";
    BASE_URL = $location.$$absUrl;
    let WELLCOME_MESSAGE = '\u0414\u043e\u0431\u0440\u043e\u0020\u043f\u043e\u0436\u0430\u043b\u043e\u0432\u0430\u0442\u044c\u002c';
    let DEFAULT_NO_USER_MESSAGE = '\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0443\u0439\u0442\u0435\u0441\u044c\u0020\u0438\u043b\u0438\u0020\u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c';
    $scope.thisUser = DEFAULT_NO_USER_MESSAGE;
    $scope.currentUserObj = currentUserObj;
    currentUserObj = null;
    $scope.currentServiceList = null;

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

    $scope.tryTransformJsonErrorMessageToString = function (errorObj) {
        if (!errorObj.hasOwnProperty("data") || !errorObj.data.hasOwnProperty("data")) {
            return "";
        }
        let errorData = errorObj.data.data;
        let message = "";
        try {
            errorData = JSON.parse(errorData);
            if (errorData.hasOwnProperty("error") && Array.isArray(errorData.error)) {
                let initMessage = "";
                message = errorData.error.reduce((prev, current) => prev + " " + current, initMessage);
            }
        } catch (e) {
            message = "" + errorData;
        }
        return message;
    }

    $scope.checkCurrentUser = function () {
        if (!hasJwt()) {
            $scope.setCurrentUser(null);
        }
        let url = BASE_URL + "auth/testuser/";
        let CheckUser = $resource(url, {}, $scope.actions);
        CheckUser.get().$promise.then(
            function (response) {
                if (response.status == 200) {
                    $scope.setCurrentUser(JSON.parse(response.data));
                    return;
                }
                $scope.setCurrentUser(null);
            }, function (error) {
                $scope.setCurrentUser(null);
            }
        );
    }


    $scope.setCurrentUser = function (userObj) {
        if (userObj
            && userObj.hasOwnProperty("secondName")
            && userObj.hasOwnProperty("name")
            && userObj.hasOwnProperty("thirdName")
            && userObj.hasOwnProperty("email")) {
            currentUserObj = userObj;
            $scope.thisUser = WELLCOME_MESSAGE + ' ' + userObj.name + ' ' + userObj.secondName;
        } else {
            currentUserObj = null;
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
        orderListContr.currentOrderList = null;
    }



    /*
     TODO It is very simplified implementation without 100% guarantee valid return value
      must be refactored to async function with checkCurrentUser call
      with await call at client
     */
    $scope.isLoggedIn = function () {
        return hasJwt();
    }

    $scope.getServiceList = function () {
        $scope.statusGetOrderList = ""
        if (!$scope.isLoggedIn()) {
            $scope.currentOrderList = null;
            return;
        }
        let url = BASE_URL + "service/list/";
        let OrderList = $resource(url, {}, mainContr.actions);
        OrderList.get().$promise.then(
            function (response) {
                if (response.status == 200) {
                    $scope.currentServiceList = (JSON.parse(response.data));
                }
            },
            function (error) {
                console.error(error);
                $scope.statusGetOrderList = $scope.tryTransformJsonErrorMessageToString(error);
            }
        );
    }


    /* UTILS */

    // Convert milliseconds to readable date, time format like: 04.07.2022, 12:56:12
    $scope.getDateString = function (mills) {
        let date = new Date(mills);
        return date.toLocaleString();
    }

    // find first object in array containing given fieldName with given fieldValue
    $scope.findByField = function (array, fieldName, fieldValue) {
        if (array && fieldName) {
            for (let i = 0; i < array.length; i++) {
                if (array[i][fieldName] == fieldValue) {
                    return array[i];
                }
            }
        }
        return null;
    }

    // simple prefix deletion for cases like id_45 -> 45
    $scope.tryTrimPrefix = function (str, prefix) {
        let splitted = str.split(prefix);
        return splitted.length == 2 ? splitted[1] : str;
    }
});
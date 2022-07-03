let fakeuslugi = angular.module("fakeuslugi", ['ngResource', 'ngRoute'])
    .config(function ($routeProvider, $locationProvider) {
        console.log("routing started");
        $locationProvider.hashPrefix('');
        $locationProvider.html5Mode(true);
        $routeProvider
            .when('/auth', {
                templateUrl: 'ajax/auth',
                controller: 'authController'
            })
            .when('/', {
                    templateUrl: 'ajax/orderlist',
                    controller: 'orderListController'
                })
            .when('/history', {
                templateUrl: 'ajax/history',
                controller: 'historyController'
            })
            .when('/order', {
                templateUrl: 'ajax/order',
                controller: 'orderController'
            })

    });
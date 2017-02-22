'use strict'

var app = angular.module('app', [
    'ui.router',
    'ngResource',
    'app.services',
    'app.controllers'

]);

app.config(function($urlRouterProvider, $stateProvider) {

    $urlRouterProvider
    
    .otherwise("/");

    $stateProvider
        .state("quote", {
            url: "/",
            templateUrl : "templates/quote.html",
            controller: "RandomQuoteCtrl"
        })
        .state("add", {
            url: "/add",
            templateUrl : "templates/add.html",
            controller: "SaveQuoteCtrl"
        })
        /*
        .state("list", {
            url: "/list",
            templateUrl : "templates/list.html",
            controller: "ListQuoteCtrl"
        })
        */
        
    .state("detail", {
        url: "/detail/:id",
        templateUrl : "templates/detail.html",
     /*   
        resolve: {
        	Author: function($stateParams) {
        		var id = $stateParams.id;
        	}
        },
        */
          controller: "DetailQuoteCtrl"
    }) 
});
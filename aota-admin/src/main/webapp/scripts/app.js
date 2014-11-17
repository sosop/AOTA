'use strict';

/**
 * @ngdoc overview
 * @name aotaApp
 * @description
 * # aotaApp
 *
 * Main module of the application.
 */
angular
  .module('aotaApp', [
    'ngResource',
    'ngRoute',
    'aotaApp.controllers',
    'aotaApp.services',
    'aotaApp.directives',
    'aotaApp.filters'
  ])
  .config(function ($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl: HOST+'views/dashboard.html',
        controller: 'DashboardCtrl'
    })
    .when('/dashboard', {
        templateUrl:  HOST+'views/dashboard.html',
        controller: 'DashboardCtrl'
    })
    .when('/statist', {
        templateUrl:  HOST+'views/statist.html',
        controller: 'StatistCtrl'
    })
    .when('/app_manage', {
        templateUrl:  HOST+'views/app_manage.html',
        controller: 'AppManageCtrl'
    })
    .when('/pkg_manage', {
        templateUrl:  HOST+'views/pkg_manage.html',
        controller: 'PkgManageCtrl'
    })
    .when('/recycle_bin', {
        templateUrl:  HOST+'views/recycle_bin.html',
        controller: 'RecycleBinCtrl'
    })
    .otherwise({
         redirectTo: '/'
    });

  });

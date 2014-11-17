'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('aotaApp.services', ['ngResource'])
	.factory('stcStatService', function ($resource) {
        return $resource(HOST+'/statistics/static', {}, {
            query: {
        		method: 'GET', 
            	isArray: false
            }
        });
    })
    .factory('dynStatService', function ($resource) {
        return $resource(HOST+'statistics/dynamic/:type/:day/:percent', {}, {
            query: {
        		method: 'GET', 
        		params: {
	        		type: '@type',
	        		day: '@day',
	        		percent: '@percent'
            	},
            	isArray: false
            }
        });
    })
    .factory('updateSqcService', function ($resource) {
        return $resource(HOST+'app/res/sequence/:appId/:sequence', {}, {
            save: {
        		method: 'POST', 
        		params: {
	        		appId: '@appId',
	        		sequence: '@sequence',
            	},
            	isArray: false
            }
        });
    })
    .factory('appService', function ($resource) {
        return $resource(HOST+'app/res/add', {}, {
            save: {
        		method: 'POST', 
        		data: {
        			appName: '@appName',
				    apkPackName: '@apkPackName',
				    appSize: '@appSize',
				    appStartGrade: '@appStartGrade',
				    appDeveloper: '@appDeveloper',
				    appDetail: '@appDetail',
				    appPermission: '@appPermission',
				    appIconUUID: '@appIconUUID',
				    appImgUUIDs: '@appIconUUID',
				    sourceChannel: '@sourceChannel',
				    versionName: '@sourceChannel',
				    versionCode: '@sourceChannel',
				    appUrlUUID: '@sourceChannel'
        		},
            	isArray: false
            },
            remove: {
            	url: HOST+'app/res/del',
            	method: 'POST',
            	data: '@ids'
            },
            pkg: {
            	url: HOST+'app/res/putpackage',
            	method: 'POST',
            	data: '@ids'
            },
            isArray: false
        });
    })
    .factory('ruleService', function ($resource) {
        return $resource(HOST+'strategy', {}, {
            query: {
        		method: 'GET', 
        		params: {
	        		id: 1,
		            upgradePeriod: '@upgradePeriod',
		            autoDownwifi: '@autoDownwifi',
		            delUpgradeapk: '@delUpgradeapk',
		            clearCache: '@clearCache'
            	},
            	isArray: false
            },
            save: {
            	url: HOST+'strategy/update',
            	method: 'POST', 
        		params: {
	        		id: 1,
		            upgradePeriod: '@upgradePeriod',
		            autoDownwifi: '@autoDownwifi',
		            delUpgradeapk: '@delUpgradeapk',
		            clearCache: '@clearCache'
            	},
            	isArray: false
            }
        });
    });


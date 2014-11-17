'use strict';

/**
 * @ngdoc function
 * @description
 * Controllers of the aotaApp
 */
angular.module('aotaApp.controllers', [])
    .run(['$rootScope', function($rootScope) {
	 	$rootScope.activeLi = function(){
	 		$('.navbar-main li.active').removeClass('active');
	 		var elem = $('.'+$rootScope.path);
	 		movePointerTo(elem);
	 		activeElem(elem);
	 	};
	 }])
    .controller('DashboardCtrl', ['$scope', '$location','$rootScope', 'stcStatService', function($scope, $location, $rootScope, Dashboard) {
        $rootScope.title = 'Dashboard';
        $rootScope.header = 'Dashboard Chart';
        $rootScope.path = "dashboard";
        $rootScope.activeLi();

        loadStyle(HOST+"styles/dashboard.min.css");
        loadScript(HOST+"bower_components/chart-js/Chart.min.js");

        var options = {
            animation: false,
            scaleFontSize: 10,
            tooltipFontSize: 12,
            tooltipCornerRadius: 2,
            scaleLineColor: "rgba(0,0,0,.06)",
            scaleShowGridLines: false,
            bezierCurve: false,
            pointDotStrokeWidth: 2,
            gradientFill: true,
        };

        // only chrome use animation
        if(isChrome()) {
            $.extend(options, {
                animation: true
            });
        }

        var darkerOptions = $.extend({}, options, {
            darkerLine: true
        });

        $scope.total = [0,0,0,0];

        var ddCtx = $('#d-d')[0].getContext("2d"),
            udCtx = $('#u-d')[0].getContext("2d"),
            dwCtx = $('#d-w')[0].getContext("2d"),
            uwCtx = $('#u-w')[0].getContext("2d");

        Dashboard.query().$promise.then(function (data){
        	var ddData = convertChartData(data.data.dd, 1, 1),
        		dwData = convertChartData(data.data.dw, 1, 7),
        		udData = convertChartData(data.data.ud, 2, 1),
        		uwData = convertChartData(data.data.uw, 2, 7);

        	$scope.total = [getArrSum(data.data.dd),getArrSum(data.data.ud),getArrSum(data.data.dw),getArrSum(data.data.uw)];

        	var ddChart = new Chart(ddCtx).Line(ddData.data, options),
	        	udChart = new Chart(udCtx).Line(udData.data, options),
	        	dwChart = new Chart(dwCtx).Line(dwData.data, darkerOptions),
	        	uwChart = new Chart(uwCtx).Line(uwData.data, darkerOptions);
        });

    }])


    .controller('StatistCtrl', ['$scope', '$rootScope', 'dynStatService', 'updateSqcService', function($scope, $rootScope, DynStat, AppSqc) {
        $rootScope.title = 'Statist';
        $rootScope.header = 'Search';
        $rootScope.path = "statist";

        $rootScope.activeLi();

        loadStyle(HOST+"styles/statist.min.css");
        loadScript(HOST+"bower_components/chart-js/Chart.min.js");

        $(window).resize(function() {
			resizeTable(230);
		});
        // set querymodel
        $scope.queryParams = {
        	type: 1,
        	day: 1,
        	percent: ($scope.percent || 100)
        };
        $scope.orderProp = "appSequence";

        var firstSearch = !$('.chart-container').hasClass('show');

        var statDayCtx = $('#stat-chart-day')[0].getContext("2d"),
        	statWeekCtx = $('#stat-chart-week')[0].getContext("2d"),
        	statDayChart = null,
        	statWeekChart = null;

        /* functions */

        $scope.queryDynStat = function() {
        	DynStat.query($scope.queryParams)
        		.$promise.then(function (data){
        			resizeTable(240);
        			var type = $scope.queryParams.type,
        				day = $scope.queryParams.day;
        			var chartData = convertChartData(data.data[0], type, day);

        			if(firstSearch){
        				$('.chart-container').animate({"height":"195px"}, 'normal', 'swing', function(){updateChart();});
        			}
        			else {
        				updateChart();
        			}

        			function updateChart(){
        				$scope.chartType = (type === 1?'Download':'Upgrade');
	    				$scope.chartIcon = chartData.icon;
	    				$scope.chartTotal = getArrSum(chartData.data.datasets[0].data);
	    				$scope.totalColor = chartData.totalColor;
	    				$scope.$digest();

    					if(day === 1) {
    						$('#stat-chart-week').removeClass('show');
    						$('#stat-chart-day').addClass('show');
    							statDayChart = new Chart(statDayCtx).Line(chartData.data, chartData.options);
    					}
    					else if(day === 7){
    						$('#stat-chart-week').addClass('show');
    						$('#stat-chart-day').removeClass('show');
    							statWeekChart = new Chart(statWeekCtx).Line(chartData.data, chartData.options);
    					}
        			}

        			$scope.apps = data.data[1];
        		});
        };

        $scope.updateSqc = function(event){
        	var elem = event.currentTarget,
        		appId = +$(elem).attr('data-id'),
        		sequence = +$(elem).val();
        	var saveParams = {
        		appId: appId,
        		sequence: sequence
        	};
        	AppSqc.save(saveParams).$promise.then(function (data){
        		if(data.status !== 0) {
        			for(var i = 0; i < $scope.apps.length; i++){
	        			if($scope.apps[i].id === appId) {
	        				$scope.apps[i].appSequence = sequence;
	        			}
	        		}
        		}
        		else {
        			alert('Update app sequence failed!');
        		}
        	});
        };

    }])


    .controller('AppManageCtrl', ['$scope', '$http', '$rootScope', 'updateSqcService', 'appService', 'ruleService', function($scope, $http, $rootScope, AppSqc, App, Rule) {
    	$rootScope.title = 'App Management';
        $rootScope.header = 'Search';
        $rootScope.path = "app_management";

        $rootScope.activeLi();

        loadStyle(HOST+"styles/app-manage.min.css");
        // store app screenshot
        var appSs1 = null,
            appSs2 = null,
            appSs3 = null,
            appSs4 = null;
        // bind event
        $(window).resize(function() {
			resizeTable(100);
		});
		$('#upload-app-btn').click(function(){
            // reset
            $scope.newApp = {
                appName: null,
                apkPackName: null,
                appSize: null,
                appStartGrade: null,
                appDeveloper: null,
                appDetail: null,
                appPermission: null,
                appIconUUID: null,
                appImgUUIDs: null,
                sourceChannel: 1,
                versionName: null,
                versionCode: null,
                appUrlUUID: null
            };
            appSs1 = null;
            appSs2 = null;
            appSs3 = null;
            appSs4 = null;
            $scope.$digest();
            $('#save-app').attr('disabled', false).html('OK');
            $('#upload-apk').attr('disabled', false).html('Upload');
            $('#upload-app form')[0].reset();
            $('.upload-area img').hide();
			$('#upload-app').modal('show');
            $('.modal-body-cover').hide();
		});

		$('.upload-area').click(function(){
			$(this).find('input[type=file]').click();
		});
		$('.upload-area input[type=file]').click(function (e) {
			e.stopPropagation();
		});
		$('#choose-apk').click(function(){
			$('#apk').click();
		});

		// apk upload
		$('#apk').change(function(){
            var file = this.files[0];
			if(getSuffix(file.name) !== 'apk') {
				alert('Please choose an apk file to upload');
				return false;
			}
            $('#apk-url').val(file.name);
            $('#upload-apk').attr('disabled', false).html('Upload');
		});
		$('#upload-apk').click(function(){
            var file = $('#apk')[0].files[0];
			if(file) {
				uploadFile({
					file: file,
                    useFormData: true,
                    fileName: 'apkFile',
					url: HOST+'app/res/parseApk',
					onProgress: function(file, loaded, total){
						$('#upload-apk').attr('disabled', true).html(Math.round(loaded / total * 100) + '%');
					},
                    onSuccess: function(file, response) {
                        $('#upload-apk').html('<i class="fa fa-check"></i>');
                        var data = JSON.parse(response);
                        if(data.status === 1) {
                            if(data.data.resultFlag === 2){
                                $scope.newApp.appUrlUUID = data.data.apkuuid;
                                $scope.newApp.appName = data.data.appName;
                                $scope.newApp.appSize = data.data.fileSize+'';
                                $scope.newApp.apkPackName = data.data.packageName;
                                $scope.newApp.appPermission = data.data.permissions;
                                $scope.newApp.versionCode = data.data.versionCode;
                                $scope.newApp.versionName = data.data.versionName;
                                $scope.$digest();
                            }
                            else if(data.data.resultFlag === 1){
                                alert('Apk parse failed');
                            }
                            else {
                                alert('Apk upload failed');
                            }
                        }
                    },
                    onFailure: function(file, response) {
                        alert('Parse file failed');
                        $('#upload-apk').attr('disabled', false).html('Upload');
                    },
                    onComplete: function(){
                        //delete file
                        $('#apk')[0].files = [];
                    }
				});
			}
            else {
                alert('Please choose an apk first');
            }
		});
        // icon & screenshot
        $('#app-icon').change(function(){
            var file = this.files[0];
            if(!isImage(file.name)) {
                alert('Please choose jpg/png/gif to upload');
                return false;
            }
            imageReader(file, function(result){
                $('#app-icon-preview').attr('src', result).fadeIn();
            });
            uploadFile({
                file: file,
                useFormData: true,
                fileName: $(this).attr('id'),
                url: HOST+'app/res/upload',
                onProgress: function(file, loaded, total){
                    // $(this).parent().find('.cover').css({
                    //     'top': (-Math.round(loaded / total * 100) + '%'),
                    //     'z-index': 12
                    // });
                },
                onSuccess: function(file, response) {
                    var data = JSON.parse(response);
                    if(data.status === 1) {
                        if(data.data[0].result === 'success'){
                            $scope.newApp.appIconUUID = data.data[0].fileUuid;
                        }
                        else {
                            alert('Image upload failed');
                        }
                    }
                },
                onFailure: function(file, response) {
                    alert('Parse file failed');
                },
                onComplete: function(){
                }
            });
        });
            
        $('.app-ss').change(function(){
            var self = this;
            var file = this.files[0];
            if(!isImage(file.name)) {
                alert('Please choose jpg/png/gif to upload');
                return false;
            }
            imageReader(file, function(result){
                $(self).parent().find('.app-ss-preview').attr('src', result).fadeIn();
            });
            uploadFile({
                file: file,
                useFormData: true,
                fileName: $(this).attr('id'),
                url: HOST+'app/res/upload',
                onProgress: function(file, loaded, total){
                    // $(this).parent().find('.cover').css({
                    //     'top': (-Math.round(loaded / total * 100) + '%'),
                    //     'z-index': 12
                    // });
                },
                onSuccess: function(file, response) {
                    var data = JSON.parse(response);
                    if(data.status === 1) {
                        if(data.data[0].result === 'success'){
                           if($(self).attr('id') === 'app-ss-1'){
                                appSs1 = data.data[0].fileUuid;
                           }
                           else if($(self).attr('id') === 'app-ss-2'){
                                appSs2 = data.data[0].fileUuid;
                           }
                           else if($(self).attr('id') === 'app-ss-3'){
                                appSs3 = data.data[0].fileUuid;
                           }
                           else if($(self).attr('id') === 'app-ss-4'){
                                appSs4 = data.data[0].fileUuid;
                           }
                        }
                        else {
                            alert('Image upload failed');
                        }
                    }
                },
                onFailure: function(file, response) {
                    alert('Parse file failed');
                },
                onComplete: function(){
                }
            });
        });
        

        $scope.addApp = function(){
            var ssArr = [appSs1,appSs2,appSs3,appSs4],
                appSs = [];
            for(var i = 0;i<ssArr.length;i++){
                if(ssArr[i]) {
                    appSs.push(ssArr[i]);
                }
            }
            $scope.newApp.appImgUUIDs = appSs.join(',');

            for(var a in $scope.newApp) {
                log($scope.newApp[a])
                if(!$.trim($scope.newApp[a])) {
                    alert('All infomations needed, please check again');
                    return false;
                }
            }
            $('#save-app').html('Uploading...').attr('disabled', true);
          
            $('.modal-body-cover').show();
            App.save($scope.newApp).$promise.then(function(data){
                if(data.status === 1) {
                    alert('Upload Success');
                    $('#upload-app').modal('hide');
                    // reset modal
                }
                else {
                    alert('App upload failed');
                    $('.modal-body-cover').hide();
                }
            });
        };


        // set querymodel
        $scope.queryParams = {
    		appName: $scope.appName,
    		sourceChannel: $scope.sourceChannel,
    		appSequence: $scope.appSequence,
    		versionName: $scope.versionName,
    		appDeveloper: $scope.appDeveloper,
    		uploadTime: $scope.uploadTime,
            trash: 0
        };

        $scope.orderProp = "appSequence";

        $scope.queryApp = function() {
    		//format time 
    		// $scope.queryParams.uploadTime = new Date($scope.queryParams.uploadTime).getTime();

	        $http.post(HOST+'app/res/list/1', $scope.queryParams).success(function (data){
	        	if(data.status !== 0) {
	        		resizeTable(100);
    				$scope.apps = data.data.appList;
	        	}
	        });
    	};

    	$scope.updateSqc = function(event){
        	var elem = event.currentTarget,
        		appId = +$(elem).attr('data-id'),
        		sequence = +$(elem).val();
        	var saveParams = {
        		appId: appId,
        		sequence: sequence
        	};
        	AppSqc.save(saveParams).$promise.then(function (data){
        		if(data.status !== 0) {
        			for(var i = 0; i < $scope.apps.length; i++){
	        			if($scope.apps[i].id === appId) {
	        				$scope.apps[i].appSequence = sequence;
	        			}
	        		}
        		}
        		else {
        			alert('Update app sequence failed!');
        		}
        	});
        };

        $scope.rule = {
            id: 1,
            upgradePeriod: null,
            autoDownwifi: null,
            delUpgradeapk: null,
            clearCache: null
        };

        // upgrade rule
        $('#upgrade-rule-btn').click(function() {
            Rule.query().$promise.then(function (data){
                $scope.rule = data.data;
            });
            $('#upgrade-rule').modal('show');
        });

        $scope.saveRule = function(){
            Rule.save($scope.rule).$promise.then(function(data){
                if(data.status === 1){
                    // alert('Rule upgrade success');
                    $('#upgrade-rule').modal('hide');
                }
                else {
                    alert('Rule upgrade failed');
                }
            });
        };

        // delete app
        $scope.deleteApp = function(){
            var ids = getTableCheckedId($('.tbody table'));
            if(!ids.length){
                alert('Please choose app to delete');
                return;
            }
            if(confirm('Do you really want to delete choosen apps?')){
                App.remove(ids).$promise.then(function(data){
                    if(data.status !== 0){
                        removeAppFromScope (ids);
                    }
                });
            }
        };

        //make package
        $scope.pkgApp = function(){
            var ids = getTableCheckedId($('.tbody table'));
            if(!ids.length){
                alert('Please choose app to make package');
                return;
            }
            App.pkg(ids).$promise.then(function(data){
                if(data.status !== 0){
                    alert('package generated');
                    removeAppFromScope (ids);
                }
            });
        };

        function removeAppFromScope (ids){
            var toDelete = [],
                indexOffset = 0;
            $.each($scope.apps,function(index){
                if($.inArray(this.id, ids) !== -1) {
                    toDelete.push(index);
                }
            });
            for(var i = 0; i<toDelete.length; i++) {
                $scope.apps.splice(toDelete[i]-indexOffset,1);
                indexOffset++;
            }
        }
    }])
    .controller('PkgManageCtrl', ['$scope', '$http', '$rootScope', 'updateSqcService', function($scope, $http, $rootScope, AppSqc) {
        $rootScope.title = 'Package Management';
        $rootScope.header = 'Search';
        $rootScope.path = "package_management";

        $rootScope.activeLi();

        // bind event
        $(window).resize(function() {
            resizeTable(100);
        });

        $scope.queryParams = {
            packageName: $scope.packageName,
            createTime: $scope.createTime,
            quantity: $scope.quantity,
            sequence: $scope.sequence
        };

        //query list
        $scope.queryPkg = function(event){
            $http.post(HOST+'/package', $scope.queryParams).success(function (data){
                if(data.status !== 0) {
                    resizeTable(100);
                    $scope.pkgs = data.data;
                }
            });
        };

        $scope.releasePkg = function(event){
            var ids = getTableCheckedId();
            if(ids.length != 1){
                alert('Please select one release');
                return;
            }
            var id = ids[0];
            $http.put(HOST+"/package/release/"+ids[0]).success(function(data){
                $.each($scope.pkgs, function(idx, item){
                    if(item.id == id){
                        item.releaseTime = data.status;
                        return false;
                    }
                });
            });
        };
        $scope.dissolutionPkg = function(event){
            var ids = getTableCheckedId();
            $http.post(HOST+"/package/dismiss", ids).success(function(data){
                if(data.status > 0 ){
                    alert("Dissolution Package Success");
                    $scope.queryPkg();
                }
            });
        };

    }])
    .controller('RecycleBinCtrl', ['$scope', '$http', '$rootScope', 'updateSqcService', function($scope, $http, $rootScope, AppSqc) {
        $rootScope.title = 'Recycle Bin';
        $rootScope.header = 'Search';
        $rootScope.path = "recycle_bin";

        $rootScope.activeLi();

        // bind event
        $(window).resize(function() {
            resizeTable(100);
        });

        $scope.orderProp = 'appSequence';

        $scope.queryParams = {
            appName: $scope.appName,
            sourceChannel: $scope.sourceChannel,
            appSequence: $scope.appSequence,
            versionName: $scope.versionName,
            appDeveloper: $scope.appDeveloper,
            uploadTime: $scope.uploadTime,
            trash:1
        };

        //query list
        $scope.queryApp = function(event){

            $http.post(HOST+'app/res/list/1', $scope.queryParams).success(function (data){
                if(data.status !== 0) {
                    resizeTable(100);
                    $scope.apps = data.data.appList;
                }
            });
        };
        $scope.doRecovery = function(event){
            var ids = getTableCheckedId();
            if(!ids.length){
                return;
            }
            $http.post(HOST+'/app/recycle/recovery', ids).success(function (data){
                if(data.status !== 0){
                    alert("Recovery Success");
                    $scope.queryApp();
                }
            });
        };

        $scope.doDelete = function(event){
            var ids = getTableCheckedId();
            if(!ids.length){
                return;
            }
            if(confirm('Do you really want to delete choosen apps thoroughly?\u000d(this operation cannot be undone)')){
                $http.post(HOST+'/app/recycle/remove', ids).success(function(data){
                    if(data.status !== 0){
                        $scope.queryApp();
                    }
                });
            }
        };

    }]);
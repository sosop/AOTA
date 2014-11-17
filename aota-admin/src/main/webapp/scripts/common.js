$(function(){


	adjustContentHeight();	

	movePointerTo($('.navbar-main li.active'));

	$(window).resize(function() {
		adjustContentHeight();
		if(!$('.pointer').hasClass('aligned')) {
			movePointerTo($('.navbar-main li.active'));
		}
	});
});
// use js cache
$.ajaxSetup({
  cache: true
});

// global config
var DEBUG = true;

// global object
var TIMER = {},
	HOST = "aota-admin/";
	// HOST = '';

function log(message) {
	if(DEBUG){
		if(window.console){
			window.console.log(message);
		}
	}
}

function loadStyle(url) {
	if (document.createStyleSheet)
	{
	    document.createStyleSheet(url);
	}
	else
	{
	    $('<link rel="stylesheet" type="text/css" href="' + url + '" />').appendTo('head'); 
	}
}

function loadScript(url) {
	$('<script src="' + url + '" /></script>').appendTo('.route-view'); 
}

function isChrome(){
	return  window.navigator.userAgent.indexOf("Chrome") !== -1 ;
}

function movePointerTo(elem) {
	if($(window).width()>975) {
		$('.pointer')
			.removeClass('aligned')
			.css({
				'top': 'auto',
				'bottom': 0,
				'left': elem.offset().left+'px',
				'width': elem.width(),
				'height': 4
			})
			.addClass('aligned');
	}
}

function activeElem(elem) {

	elem.addClass('active');
}

function adjustContentHeight() {
	var avaliableHeight = $(window).height() - 168;
	$('#main-content-body').css('height', avaliableHeight);
}

function resizeTable(extra) {
	var avaliableHeight = $('#main-content-body').height() - $('.form-inline').height() - $('.table-result thead').height() - extra;
	var scrollbarWidth = getScrollbarWidth();
	$('.result').css('padding-right', scrollbarWidth);
	$('.tbody').css({
		'overflow-y': 'scroll',
		'height': avaliableHeight,
		'margin-right': -scrollbarWidth
	});
}

function obj2Array(obj) {
	var arr = [],
		i = 0;
	while(obj[i] !== undefined) {
		arr = arr.concat(obj[i]);
		i++;
	}
	return arr;
}

function getArrSum(arr) {
	var sum = 0;
	for(var i = 0; i<arr.length; i++) {
		if(typeof +arr[i] !== 'number') continue;
		sum += arr[i];
	}
	return sum;
}

function convertChartData(arr, type, day) {
    var dayLabel = ["", "3", "6", "9", "12", "15", "18", "21", "24", "(hours)"],
    	weekLabel = ["", "1", "2", "3", "4", "5", "6", "7", "(day)"];
	var chartData = arr;
	if(day === 7){
		chartData = chartData.slice(0,7);
	}
	chartData = chartData.concat(null);
	chartData = [].concat.apply([null], chartData);
	
	var fillColor = null,
		labels = null,
		strokeColor = null,
		pointHighlightStroke = null,
		icon = null,
		totalColor = null;

	var options = {
        animation: true,
        scaleFontSize: 10,
        tooltipFontSize: 12,
        tooltipCornerRadius: 2,
        scaleLineColor: "rgba(0,0,0,.06)",
        scaleShowGridLines: false,
        bezierCurve: false,
        pointDotStrokeWidth: 2,
        gradientFill: true
    };
    // only chrome use animation
    // if(isChrome()) {
    //     $.extend(options, {
    //         animation: true
    //     });
    // }

	if(type === 1) {
		if(day === 1) {
			labels = dayLabel;
			strokeColor = '#009cde';
			pointHighlightStroke = 'rgba(0, 156, 222,0.5)';
			fillColor = ["rgba(0, 156, 222, 0.15)", "rgba(0, 156, 222, 0.09)", "rgba(0, 156, 222, 0)"];
			icon = 'sprite-download-h';
		}
		else if(day === 7) {
			labels = weekLabel;
			strokeColor = '#a3be8c';
			pointHighlightStroke = 'rgba(163, 190, 140,0.5)';
			fillColor = ["rgba(163, 190, 140, 0.24)", "rgba(163, 190, 140, 0.15)", "rgba(163, 190, 140, 0)"];
			icon = 'sprite-download-d';
			// darker line options
	        $.extend(options, {
	            darkerLine: true
	        });
		}
	}
	else if(type === 2) {
		if(day === 1) {
			labels = dayLabel;
			strokeColor = '#b48ead';
			pointHighlightStroke = 'rgba(180, 142, 173,0.5)';
			fillColor = ["rgba(180, 142, 173, 0.15)", "rgba(180, 142, 173, 0.09)", "rgba(180, 142, 173, 0)"];
			icon = 'sprite-upgrade-h';
		}
		else if(day === 7) {
			labels = weekLabel;
			strokeColor = '#ebcb8b';
			pointHighlightStroke = 'rgba(235, 203, 139,0.5)';
			fillColor = ["rgba(235, 203, 139, 0.24)", "rgba(235, 203, 139, 0.15)", "rgba(235, 203, 139, 0)"];
			icon = 'sprite-upgrade-d';
			// darker line options
	        $.extend(options, {
	            darkerLine: true
	        });
		}
	}
	return {
		data: {
			labels: labels,
            datasets: [{
                label: "chart",
                fillColor: fillColor,
                strokeColor: strokeColor,
                pointColor: "rgba(255,255,255,1)",
                pointStrokeColor: strokeColor,
                pointHighlightFill: "#fff",
                pointHighlightStroke: pointHighlightStroke,
                data: chartData
            }]
		},
		options: options,
		icon: icon,
		totalColor: strokeColor
	};
}

function getScrollbarWidth() {
    var outer = document.createElement("div");
    outer.style.visibility = "hidden";
    outer.style.width = "100px";
    outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps

    document.body.appendChild(outer);

    var widthNoScroll = outer.offsetWidth;
    // force scrollbars
    outer.style.overflow = "scroll";

    // add innerdiv
    var inner = document.createElement("div");
    inner.style.width = "100%";
    outer.appendChild(inner);        

    var widthWithScroll = inner.offsetWidth;

    // remove divs
    outer.parentNode.removeChild(outer);
    return widthNoScroll - widthWithScroll;
}

//table checked item id form check
function getTableCheckedId($table){
    $table = $table || $(".table-result");
    var arr = [];
    $table.find(".chk-item.checked[data-id]").each(function(){
        arr.push(+$(this).attr("data-id"));
    });
    return arr;
}

function getSuffix(str) {
	var strArr = str.split('.');
	return strArr[strArr.length-1];
}

function isImage(str) {
	return getSuffix(str) === 'jpg' || getSuffix(str) === 'png' || getSuffix(str) === 'gif';
}

function uploadFile(uploadParams){
	var file = uploadParams.file,
		useFormData = uploadParams.useFormData,
		fileName = uploadParams.fileName,
		url = uploadParams.url,
		onProgress = uploadParams.onProgress,
		onSuccess = uploadParams.onSuccess,
		onFailure = uploadParams.onFailure,
		onComplete = uploadParams.onComplete;

	var xhr = new XMLHttpRequest();
	if (xhr.upload) {
		// 上传中
			xhr.upload.addEventListener("progress", function(e) {
				onProgress(file, e.loaded, e.total);
			}, false);

	}
	var data = null;
	if(useFormData) {
		data = new FormData();
		data.append(fileName, file);
	}
	else data = file;
	
		// 文件上传成功或是失败
	xhr.onreadystatechange = function(e) {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				onSuccess(file, xhr.responseText);
			} else {
				onFailure(file, xhr.responseText);		
			}
			onComplete();
		}
	};

	// 开始上传
	xhr.open("POST", url, true);
	xhr.setRequestHeader("X_FILENAME", file.name);
	xhr.send(data);
}		

function imageReader(file, callback){
	var reader = new FileReader();  
    reader.readAsDataURL(file);  
    reader.onload=function(e){
    	if(typeof callback === 'function') callback(e.target.result);
    }; 
}

// function adjustModal(model){
// 	var dialog = model.find('.modal-dialog');
// 	log(dialog.height())
// 	dialog.css('margin-top', ($(window).height()-dialog.height())/2);
// }
'use strict';

/* Filters */

angular.module('aotaApp.filters', [])
  	.filter('size', function() {
	  	return function(size) {
	  		if(!size) {
	  			return '';
	  		}
	  		return size<1024*1024 ? (size/(1024)).toFixed(2)+' K' : (size/(1024*1024)).toFixed(2)+' M';
	  	};
	})
	.filter('time', function() {
		return function(timestamp) {
			if(!timestamp) {
	  			return '';
	  		}
			var d = new Date(timestamp);
			var month = (d.getMonth()+1)>9 ? (d.getMonth()+1) : ('0'+(d.getMonth()+1)),
				date = d.getDate()>9 ? d.getDate() : ('0'+d.getDate()),
				hour = d.getHours()>9 ? d.getHours() : ('0'+d.getHours()),
				minute = d.getMinutes()>9 ? d.getMinutes() : ('0'+d.getMinutes()),
				second = d.getSeconds()>9 ? d.getSeconds() : ('0'+d.getSeconds());
			return d.getFullYear() + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second;
		};
	})
	.filter('number', function(){
		return function (targerNumber) {
	        if(targerNumber < 1000) {
	            return targerNumber;
	        }
	        var num = targerNumber.toString().split('.');
	        var count = 0,
	            tmp = num[0].split('');
	        tmp.reverse();
	        var tmp_copy = tmp.slice();
	        for(var i = 0; i < tmp_copy.length; i += 3) {
	            if(i === 0) {
	                continue;
	            }
	            tmp.splice(i + count, 0, ',');
	            count++;
	        }
	        tmp.reverse();
	        num[0] = tmp.join('');
	        if(num.length > 1) {
	            return num.join('.');
	        } else return num[0];
	    };
	});

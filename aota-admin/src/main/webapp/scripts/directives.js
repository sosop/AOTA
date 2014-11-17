'use strict';
/* Directives */
angular.module('aotaApp.directives', [])
	.directive('selectEmu', function () {
	    return {
	    	link : function(scope,elem,attr){
	    		// log(elem)
	    		elem.delegate('.dropdown-menu a', 'click', function(){
	    			elem.find('.choosen-value').html($(this).html());
	    		});
	        }
	    };
	})
	.directive('showStar', function () {
	    return {
	    	restrict : 'AE',
	    	replace : false,
	    	template: '<i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i>',
    		link: function(scope, elem, attr) {
    			var starGrade = elem.attr('data-grade');
	        	var stars = elem.find('i');
	        	for(var s = 0; s<scope.app.appStartGrade; s++){
	        		$(stars[s]).attr('class', 'fa fa-star');
	        	}
	        }
	    };
	})
	.directive('chkbox', function () {
		return {
	    	restrict : 'E',
	    	replace : true,
	    	template: '<div class="checkbox-group chk-item"><div class="custom-checkbox bordered"><i class="fa fa-check"></i></div><label for=""></label></div>',
	    	compile : function(tElem, attr){
	    		$('tr').delegate('.chk-item', 'click',  function(){
	    			$(this).toggleClass('checked');
	    		});
	    		return function(scope, elem, attr) {
		        	elem.attr('data-id', scope.app.id);
		        };
	        }
	    };
	})
	.directive('chkboxAll', function () {
		return {
	    	restrict : 'E',
	    	replace : true,
	    	template: '<div class="checkbox-group chk-all"><div class="custom-checkbox bordered"><i class="fa fa-check"></i></div><label for=""></label></div>',
	    	compile : function(tElem, attr){
	    		$('tr').delegate('.chk-all', 'click',  function(){
	    			$(this).toggleClass('checked');
	    			// log()
	    			if($(this).hasClass('checked')){
	    				$('.chk-item').addClass('checked');
	    			}
	    			else {
	    				$('.chk-item').removeClass('checked');
	    			}
	    		});
	    		// return function(scope, elem, attr) {
		     //    	var stars = elem.find('i');
		     //    	for(var s = 0; s<scope.app.appStartGrade; s++){
		     //    		$(stars[s]).attr('class', 'fa fa-star');
		     //    	}
		     //    }
	        }
	    };
	})
	.directive('addHost', function () {
		return {
	    	restrict : 'A',
	    	compile : function(tElem, attr){
	    		tElem.attr('href', HOST+tElem.attr('href'));
	        }
	    };
	});
	// .directive('radioGroup', function () {
	// 	return {
	//     	restrict : 'E',
	//     	replace : true,
	//     	template: '<div class="radio-group"><div class="custom-radio bordered checked" data-id="yes"><i></i></div><label data-for="yes">Yes</label>'+
	//     			'<div class="custom-radio bordered" data-id="no"><i></i></div><label data-for="no">No</label></div>',
	//     	link : function(scope, elem, attr) {
	//     		elem.attr('ng-model', attr.model);
	//     		if(scope.rule[attr.model] === 1){
	//     			elem.find('[data-id=yes]').addClass('checked');
	//     			elem.find('[data-id=no]').removeClass('checked');
	//     		}
	//     		else if(scope.rule[attr.model] === 0){
	//     			elem.find('[data-id=yes]').removeClass('checked');
	//     			elem.find('[data-id=no]').addClass('checked');
	//     		}
	//     		elem.find('.custom-radio').attr('name',attr.name)
	//     		.click(function(){
	//     			log(scope.rule)
	//     			// $('.custom-radio[name='+$(this).attr('name')+'].checked').removeClass('checked');
	//     			// $(this).addClass('checked');
	//     		});
	//     		elem.find('label').click(function(){
	//     			// elem.parent().find('[data-id='+$(this).attr('data-for')+']').click();
	//     		})

	//         }
	//     }
	// })
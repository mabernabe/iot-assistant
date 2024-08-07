iotAssistant.directive('includeReplace', function () {
    return {
        require: 'ngInclude',
        restrict: 'A', /* optional */
        link: function (scope, el, attrs) {
            el.replaceWith(el.children());
        }
    };
});

iotAssistant.directive('alias', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var splits = attrs['alias'].trim().split(/\s+as\s+/);
            scope.$watch(splits[0], function(val) {
                scope.$eval(splits[1]+'=('+splits[0]+')');
            });
        }
    };
});

iotAssistant.filter('trustAsResourceUrl', ['$sce', function($sce) {
    return function(val) {
        return $sce.trustAsResourceUrl(val);
    };
}])

iotAssistant.directive('setLinkActiveOnRouteChange', ['$location', function ($location) {
	return {
		restrict: 'A', //use as attribute 
		replace: false,
		link: function (scope, elem) {
			//after the route has changed
			scope.$on("$routeChangeSuccess", function () {
				var hrefs = ['/#' + $location.path(),
					'#' + $location.path(), //html5: false
					$location.path()]; //html5: true
				angular.forEach(elem.find('a'), function (a) {
					a = angular.element(a);
					if (-1 !== hrefs.indexOf(a.attr('href'))) {
						a.parent().addClass('active');
						a.parent().parent().closest('li').addClass('active');
					} else {
						a.parent().removeClass('active');   
					};
				});     
			});
		}
	}
}]);
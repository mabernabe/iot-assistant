iotAssistant.config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    	cfpLoadingBarProvider.parentSelector = '#loading-bar-container';
        cfpLoadingBarProvider.spinnerTemplate = '<div class="loader"></div>';   
    	cfpLoadingBarProvider.latencyThreshold = 500;
 }])
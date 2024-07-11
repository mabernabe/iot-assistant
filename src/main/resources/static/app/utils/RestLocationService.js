
utilsModule.service('RestLocationService', function ($location) {
    this.restlocation = "http://" + $location.host() + ":" + $location.port() + "/api/";
});
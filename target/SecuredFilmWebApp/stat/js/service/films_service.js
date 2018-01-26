use strict';
 
angular.module('SecuredFilmWebApp').factory('FilmService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_GET_ACCOUNTS_URI = 'http://localhost:8080/SecuredFilmWebApp/films.json'; 
    
    var factory = {getData: getData};
    
    return factory;
    
    function getData() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_GET_ACCOUNTS_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while retrieving accounts');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
}]);
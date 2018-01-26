'use strict';
 
angular.module('SecuredFilmWebApp').controller('FilmController', ['$scope', 'FilmService', function($scope, passedAccountService){
    var self = this;
    self.accounts=[];
    
    fetchAllFilms();
    
    function fetchAllFilms(){
        passedAccountService.getData()
            .then(
            function(d) {
                console.log("starting to fetch films...");
                self.films = d;
            },
            function(errResponse){
                console.error('Error while fetching accounts');
            }
        );
    }
}]);
 
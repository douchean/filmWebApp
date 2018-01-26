<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Films with angularjs</title>
</head>
<body ng-app="SecuredFilmWebApp">
    <div class="generic-container" ng-controller="FilmController as ctrl">
        <table border="1">
            <tr>
                <td>ID</td>
                <td>Title</td>
                <td>Year</td>  
                <td>Rating</td>
            </tr>
            <tbody>
                <tr ng-repeat="film in films">
                    <td><span ng-bind="film.idFilm"></span></td>
                    <td><span ng-bind="film.title"></span></td>
                    <td><span ng-bind="film.year"></span></td>
                    <td><span ng-bind="film.rating"></span></td>
                </tr>
            </tbody>
        </table>
    </div>
 
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script type="text/javascript">
            var app = angular.module('SecuredFilmWebApp',[]);
            app.controller('FilmController', function($scope, $http){
                $http.get('http://localhost:8080/SecuredFilmWebApp/films.json')
                     .then(
                        function (response) {
                            console.log("starting to fetch films...");
                            $scope.films = response.data;
                        },
                        function(errResponse){
                            console.error('Error while retrieving accounts');
                        })
            });
    </script>
</body>
</html>
 
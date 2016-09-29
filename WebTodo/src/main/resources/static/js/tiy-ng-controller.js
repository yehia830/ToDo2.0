angular.module('TIYAngularApp', [])
   .controller('SampleController', function($scope, $http) {
//        $scope.name = "James";
//        $scope.todos = {};

        $scope.getTodos = function() {
            console.log("In getTodos function in js controller!");
            $scope.name = "testName";

            $http.get("/todo.json")
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.todos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };

        $scope.addTodos = function() {
            console.log("In addTodos function in js controller!");
            console.log("About to add the following todo " + JSON.stringify($scope.newToDo));
//            $scope.name = "testName";

            $http.post("/addToDo.json", $scope.newToDo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.todos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };

        $scope.deleteTodo = function(todoID) {
            console.log("In deleteTodo method in js controller!");
            console.log("About to delete the todo with id: " + todoID);

            $http.get("deleteToDo.json?todoID=" + todoID)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.todos = response.data;
                        },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                        });
        };

        $scope.toggleTodo = function(todoID) {
            console.log("In toggleTodo() method in js controller!");
            console.log("About to toggle the todo with id: " + todoID);

            $http.get("toggleToDo.json?todoID=" + todoID)
                .then(
                    function successCallback(response) {
                    console.log(response.data);
                    console.log("Adding data to scope");
                    $scope.todos = response.data;
                    },
                    function errorCallback(response) {
                    console.log("Unable to get data");
                    });
        };


        $scope.newToDo = {};
//        $scope.currentUser = {};
    });
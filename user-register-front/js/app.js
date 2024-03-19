var app = angular.module('app', []);

app.controller('listUsers', ['$scope', '$http', ($scope, $http)=> {

    var API_URL = "http://localhost:8080"

    $scope.users = [];
    $scope.passValidators = {
        classNumber: 'requirement-refused',
        iconNumber: 'fa-solid fa-circle-xmark',
        classSymbols: 'requirement-refused',
        iconSymbols:  'fa-solid fa-circle-xmark',
        classLowerCase: 'requirement-refused',
        iconLowerCase:  'fa-solid fa-circle-xmark',
        classUpperCase: 'requirement-refused',
        iconUpperCase: 'fa-solid fa-circle-xmark',
        classMinimumCaractes: 'requirement-refused',
        iconMinimumCaractes: 'fa-solid fa-circle-xmark'
    };
    $scope.requestUser = {};
    $scope.form = {}
    $scope.showPassword = true;
    $scope.showPopUp = false;
    $scope.showDeletePopUp = false;
    $scope.submitInsert = true;

    $scope.selectPasswordWeight = (user) => {
        switch(user.passwordWeight) {
            case 'VERY_STRONG':
                return "pass-very-strong";
            case 'STRONG':
                return "pass-strong";
            case 'MEDIUM':
                return "pass-medium";
            case 'WEAK':
                return "pass-weak";
            case 'VERY_WEAK':
                return "pass-very-weak";
            default:
                return "pass-very-weak";
        }
    }
    
    $scope.showUsers = () => {
        $http.get(`${API_URL}/user/get-all-user`)
        .then((response) => {
            $scope.users = response.data
        })
    
    }

    $scope.submitNewUser = () => {

        var user = {
            name: $scope.form.name,
            password: $scope.form.password,
            idHierarchical: parseInt(document.getElementById("hierarchical-user").value)
        }

        $http.post(`${API_URL}/user/insert-new-user`, user)
            .then((response) => {
                $scope.users = [];
                $scope.users = response.data;
                $scope.closePopUp();
                $scope.form = {}
            })
    }

    $scope.submitUpdateUser = () => {
        var user = {
            id: $scope.requestUser.id,
            name: $scope.form.name,
            password: $scope.requestUser.password,
            idHierarchical: parseInt(document.getElementById('hierarchical-user').value),
            passwordScore: $scope.requestUser.passwordScore,
            passwordWeight: $scope.requestUser.passwordWeight
        }

        $http.put(`${API_URL}/user/update-user`, user)
            .then((response) => {
                $scope.users = [];
                $scope.users = response.data;
                $scope.closePopUp();
                $scope.form = {}
                requestUser = {}
            })
    }

    $scope.deleteUser = () => {
        $http.delete(`${API_URL}/user/delete-user/${$scope.requestUser.id}`)
            .then((response) => {
                $scope.users = [];
                $scope.users = response.data;
                $scope.closeDeletePopUp();
                $scope.requestUser = {}
            })
    }

    $scope.submitUserRequest = () => {
        if($scope.submitInsert) {
            $scope.submitNewUser();
        } else {
            $scope.submitUpdateUser();
        }
    }

    $scope.showPopUpUser = (user) => {
        $scope.showPopUp = true;
        var title = user ? "Editar usuário" : "Adicionar usuário" ;
        $scope.popUp_title = title;

        $scope.form.name = user ? user.name : "";
        $scope.showPassword = user ? false : true;
        $scope.submitInsert = user ? false : true;
        $scope.requestUser = user;

        
        if(user && user.idHierarchical) {
            document.getElementById('hierarchical-user').value = user.idHierarchical;
        } else {
            document.getElementById('hierarchical-user').value = "";
        }

    }

    $scope.showPopUpDelete = (user) => {
        $scope.requestUser = user;
        $scope.name = user.name;
        $scope.showDeletePopUp = true;
    }

    $scope.closePopUp = () => {
        $scope.showPopUp = false
    }

    $scope.closeDeletePopUp = () => {
        $scope.showDeletePopUp = false
    }

    $scope.hierarchicalUser = (user) => {

    }

    $scope.validatePass = () => {
        var currentPass = $scope.form.password;
        $http.post(`${API_URL}/password/validate-password-weight`, currentPass)
           .then((response) => {
            $scope.passValidators = {
                classNumber: response.data.numbers ? 'requirement-passed' : 'requirement-refused',
                iconNumber: response.data.numbers ? 'fa-solid fa-circle-check': 'fa-solid fa-circle-xmark',
                classSymbols: response.data.symbols ? 'requirement-passed' : 'requirement-refused',
                iconSymbols: response.data.symbols ? 'fa-solid fa-circle-check': 'fa-solid fa-circle-xmark',
                classLowerCase: response.data.lowerCaseLetters ? 'requirement-passed' : 'requirement-refused',
                iconLowerCase: response.data.lowerCaseLetters ? 'fa-solid fa-circle-check': 'fa-solid fa-circle-xmark',
                classUpperCase: response.data.uppercaseLetters ? 'requirement-passed' : 'requirement-refused',
                iconUpperCase: response.data.uppercaseLetters ? 'fa-solid fa-circle-check': 'fa-solid fa-circle-xmark',
                classMinimumCaractes: response.data.minimumCaracteres ? 'requirement-passed' : 'requirement-refused',
                iconMinimumCaractes: response.data.minimumCaracteres ? 'fa-solid fa-circle-check': 'fa-solid fa-circle-xmark'
            };
        })
    }

    $scope.hasHierarchy = (user) => {
        if(user.idHierarchical) {
            return 'has-hierarchy';
        }
        return '';

    }

}])
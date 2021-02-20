window.onload = function() {
    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    document.getElementById("date").setAttribute("min", (yyyy - 118) + '-' + mm + '-' + (dd));
    document.getElementById("date").setAttribute("max", (yyyy - 18) + '-' + mm + '-' + (dd));
}

let password = document.getElementById("password");
let confirmPassword = document.getElementById("confirmPassword");

function validatePassword(){
    if(password.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity("Passwords do not match");
    } else {
        confirmPassword.setCustomValidity('');
    }
}


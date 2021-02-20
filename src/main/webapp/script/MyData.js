window.onload = function () {
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

let changeDataButton = document.getElementById("changeButton");
let undoButton = document.getElementById("undoButton");
let data = document.getElementById("list");
let changeForm = document.getElementById("changeForm");
let exception = document.getElementById("exception");

function changeDataClick() {
    if (exception != null) {
        exception.innerHTML = "";
    }
    data.style.visibility = "hidden";
    changeDataButton.style.visibility = "hidden";
    undoButton.style.visibility = "visible";
    changeForm.style.display = "flex";
}

function undoButtonClick() {
    changeForm.style.display = "none";
    undoButton.style.visibility = "hidden";
    data.style.visibility = "visible";
    changeDataButton.style.visibility = "visible";
}



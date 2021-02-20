window.onload = function() {
    let today = new Date();
    let mm = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    let dd = today.getDate();
    if (mm < 10) {
        mm = '0' + mm
    }
    if (dd < 10) {
        dd = '0' + dd
    }
    startDate.setAttribute("min", (yyyy) + '-' + mm + '-' + (dd));
    dataChange();
    console.log(document.getElementById("select").options[0].id);
    document.getElementById("teacherID").value = document.getElementById("select").options[0].id;
}
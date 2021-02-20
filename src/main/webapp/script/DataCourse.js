let startDate = document.getElementById("startDate");
let endDate = document.getElementById("endDate");

function dataChange(){
    endDate.setAttribute("min", startDate.value)
    if(endDate.value.localeCompare(startDate.value) === -1){
        endDate.value = startDate.value;
    }
}
function changeID(){
    let select = document.getElementById("select");
    document.getElementById("teacherID").value = select.options[select.selectedIndex].id;
}


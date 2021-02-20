function noUsers() {
    document.getElementById("userList").style.display = "none";
}

function unbanUser(elem) {
    document.getElementById("userID").value = elem.id;
    document.getElementById("command").value = "setBanStatus"
    document.getElementById("banStatus").value = "unban";
    document.getElementById("userForm").submit();
}

function banUser(elem) {
    document.getElementById("userID").value = elem.id;
    document.getElementById("command").value = "setBanStatus"
    document.getElementById("banStatus").value = "ban";
    document.getElementById("userForm").submit();
}

function upToAdmin(elem) {
    document.getElementById("userID").value = elem.id;
    document.getElementById("command").value = "riseUserPosition"
    document.getElementById("userRaise").value = "admin";
    document.getElementById("userForm").submit();
}

function upToTeacher(elem) {
    document.getElementById("userID").value = elem.id;
    document.getElementById("command").value = "riseUserPosition"
    document.getElementById("userRaise").value = "teacher";
    document.getElementById("userForm").submit();
}

function downToStudent(elem) {
    document.getElementById("userID").value = elem.id;
    document.getElementById("command").value = "declineUserPosition"
    document.getElementById("userDecline").value = "student";
    document.getElementById("userForm").submit();
}

function updateCourse(elem) {
    document.getElementById("courseIDGet").value = elem.id;
    document.getElementById("commandGet").value = "adminUpdateCourse"
    document.getElementById("courseFormGet").submit();
}

function createCourse() {
    document.getElementById("commandGet").value = "adminCreateCourse";
    document.getElementById("courseFormGet").submit();
}

function removeCourse(elem) {
    document.getElementById("courseID").value = elem.id;
    document.getElementById("command").value = "adminRemoveCourse"
    document.getElementById("courseForm").submit();
}
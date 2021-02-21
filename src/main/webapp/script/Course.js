const params = new URLSearchParams(window.location.search);

window.onload = function () {
    if (params.has('sortBy')) {
        switch (params.get('sortBy')) {
            case 'NameAsc':
            case 'ИмяAsc':
                document.getElementById("nameHeader").classList.add('headerSortDown');
                break;
            case 'Term':
            case 'Длительность':
                document.getElementById("termHeader").classList.add('headerSortUp');
                break;
            case 'TermAsc':
            case 'ДлительностьAsc':
                document.getElementById("termHeader").classList.add('headerSortDown');
                break;
            case 'Student count':
            case 'Количество студентов':
                document.getElementById("studentCountHeader").classList.add('headerSortUp');
                break;
            case 'Student countAsc':
            case 'Количество студентовAsc':
                document.getElementById("studentCountHeader").classList.add('headerSortDown');
                break;
            case 'Name':
            case 'Имя':
                document.getElementById("nameHeader").classList.add('headerSortUp');
        }
    } else {
        document.getElementById("nameHeader").classList.add('headerSortUp');
         params.set("sortBy", document.getElementById("nameHeader").innerText);
    }
    switch (params.get('courseType')) {
        case 'finished':
            document.querySelector('.container').style.height = '100%';
            document.querySelector('body').style.height = '136%';
            break;
        case 'notStarted':
        case 'inProgress':
            document.querySelector('.container').style.height = '80%';
            document.querySelector('body').style.height = '116%';
    }
}

function headerClick(elem) {
    if (params.get('sortBy') === elem.textContent) {
        document.getElementById("sortBy").value = elem.textContent + "Asc"
    } else {
        document.getElementById("sortBy").value = elem.textContent;
    }
    changeCourse();
}

function courseRegister(elem) {
    document.getElementById("courseID").value = elem.id;
    document.getElementById("StudentEnrollOnCourse").submit();
}

function changeTeacher() {
    let select = document.getElementById("select");
    if (select.value === "All teachers") {
        document.getElementById("sortBy").value = params.get('sortBy');
        document.getElementById("courseForm").submit();
    } else {
        document.getElementById("teacherID").value = select.options[select.selectedIndex].id;
        document.getElementById("teacherNameAndSurname").value = select.options[select.selectedIndex].value;
        document.getElementById("teacherForm").submit();
    }
}

function changeCourse() {
    let select = document.getElementById("select");
    if (select != null) {
        if (select.value === "All courses" || select.value === "Все курсы") {
            document.getElementById("courseName").value = "%";
        } else {
            document.getElementById("courseName").value = select.options[select.selectedIndex].value;
        }
    }
    document.getElementById("courseForm").submit();
}

function blockContent() {
    /*  document.getElementById("course").style.display = "none";*/
}
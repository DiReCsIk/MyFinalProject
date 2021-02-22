let table = document.getElementById("studentTable");
let lastText;

document.querySelector('[contenteditable]').addEventListener('paste',function(e) {
    e.preventDefault();
    return false;
});

function tdOnClick(elem) {
    lastText = elem.innerHTML;
}

function tdOnBlur(elem) {
     if (elem.innerHTML > 100 || elem.innerHTML < 0 || elem.innerHTML === '') {
        elem.innerHTML = lastText;
    }

    if (lastText !== elem.innerHTML) {
        document.getElementById("studentID").value = elem.id;
        document.getElementById("mark").value = elem.innerHTML;
        document.getElementById("studentSetMark").submit();
    }
}

table.onkeypress = (event) => {
    if (isNaN(event.key) || event.target.innerText.trim().length > 2) {
        event.preventDefault();
    }
}


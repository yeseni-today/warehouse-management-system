
window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
};

function setVisible(bool) {
    var code = document.getElementById("codeLi");
    if (bool) {
        code.style.display = "block";
    } else {
        code.style.display = "none";
    }
}

function alert(info) {
    alert(info);
}

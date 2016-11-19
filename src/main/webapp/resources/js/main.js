window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
};

function setVisible(bool) {
    var code = document.getElementById("bar_code");
    if (bool) {
        code.style.display = "block";
    } else {
        code.style.display = "none";
    }
}

function alert_info(info) {
    alert(info);
}

function openPopAdd(code, name) {

    var popAdd = document.getElementsByClassName('pop_add-bg');
    var a = document.getElementsByName("itemCode");
    var b = document.getElementsByName("itemName");
    // alert("code:" + code + "name:" + name + "length:" + popAdd.length+"itemCode"+b.length);
    // var itemCode = popAdd[0].getElementsByName("itemCode");
    // var itemName = popAdd[0].getElementsByName("itemName");
    a[1].value = code;
    b[1].value = name;
    popAdd[0].style.display = "block";
}

function closePopAdd() {
    var popAdd = document.getElementsByClassName('pop_add-bg');
    popAdd[0].style.display = "none";
}

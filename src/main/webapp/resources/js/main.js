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

function addItem() {
    closePopAdd();
    var da = $("applyform").serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
        }
    })
}

function openPopAdd(code, name) {
    var popAddBg = document.getElementsByClassName('pop-bg');
    var popAdd = document.getElementsByClassName('pop');
    var a = document.getElementsByName("itemCode");
    var b = document.getElementsByName("itemName");
    a[1].value = code;
    b[1].value = name;
    popAddBg[0].style.display = "block";
    setTimeout(function () {
        popAddBg[0].style.background = "rgba(181, 181, 181, 0.5)";
        popAdd[0].style.transform = "scale(1,1)"
    }, 1);

}

function closePopAdd() {
    var popAddBg = document.getElementsByClassName('pop-bg');
    var popAdd = document.getElementsByClassName('pop');
    popAddBg[0].style.background = "rgba(181, 181, 181, 0)";
    popAdd[0].style.transform = "scale(0,0)";
    setTimeout(function () {
        popAddBg[0].style.display = "none";
    }, 500);
}

function openPopDetails(itemForm) {
    showLoading();
    var item = $
    console.log(itemForm.itemCode);
    // var popAddBg = document.getElementsByClassName('pop-bg');
    // var popAdd = document.getElementsByClassName('pop');
    // var itemName = document.getElementsByName("itemName");
    // var itemCategory = document.getElementsByName("itemCategory");
    // var itemCount = document.getElementsByName("itemCount");
    // var itemPrice = document.getElementsByName("itemPrice");
    // var itemSpec = document.getElementsByName("itemSpec");
    // var itemCompany = document.getElementsByName("itemCompany");
    // var itemCount = document.getElementsByName("itemCount");
    // var itemPrice = document.getElementsByName("itemPrice");
    // var billCode = document.getElementsByName("billCode");
    // var storageLocation = document.getElementsByName("storageLocation");
    //
    //
    // popAddBg[0].style.display = "block";
    // setTimeout(function () {
    //     popAddBg[0].style.background = "rgba(181, 181, 181, 0.5)";
    //     popAdd[0].style.transform = "scale(1,1)"
    // }, 1);

}

function closePopAdd() {
    var popAddBg = document.getElementsByClassName('pop-bg');
    var popAdd = document.getElementsByClassName('pop');
    popAddBg[0].style.background = "rgba(181, 181, 181, 0)";
    popAdd[0].style.transform = "scale(0,0)";
    setTimeout(function () {
        popAddBg[0].style.display = "none";
    }, 500);
}

function showLoading() {
    $("#loading").css("top", window.innerHeight / 2 - 16 + "px");
    $("#loading").css("left", window.innerWidth / 2 - 16 + "px");
    $("#topdiv").css("display", "block");
}
function hideLoading() {
    $("#topdiv").css("display", "none");
}

function deleteStroageItem(itemCode,index) {
    showLoading();
    $.ajax({
        url:"/storage/add/deleteItem",
        data:{"itemCode":itemCode},
        type:"get",
        success:function (result) {
            setTimeout(function () {
                hideLoading();
            },1000);
            var i = index+1;
            $('#storagetable tr:eq('+i+')').remove()
        }
    });
}
function deleteAll() {

}

function two(two) {
    var three =$.parseJSON(two);
   console.log( three);
   console.log(three.itemCode);
}






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
    closePop();
    var da = $("applyform").serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
        }
    })
}

function openPop() {
    var popAddBg = document.getElementsByClassName('pop-bg');
    var popAdd = document.getElementsByClassName('pop');

    popAddBg[0].style.display = "block";
    setTimeout(function () {
        popAddBg[0].style.background = "rgba(181, 181, 181, 0.5)";
        popAdd[0].style.transform = "scale(1,1)"
    }, 1);
}
function openPopAdd(code, name) {

    document.getElementsByName("itemCode")[1].value = code;
    document.getElementsByName("itemName")[1].value = name;
    openPop();
}
function openPopDetails(itemForm) {
    console.log(itemForm);
    var item = $.parseJSON(itemForm);
    console.log(item.itemCount);

    $("[name='itemName']").val(item.itemName);
    $("[name='itemCategory']").val(item.itemCategoryID);
    $("[name='itemCount']").val(item.itemCount);
    $("[name='itemPrice']").val(item.itemPrice);
    $("[name='itemSpec']").val(item.itemSpec);
    $("[name='itemCompany']").val(item.itemCompanyID);
    $("[name='billCode']").val(item.billCode);
    $("[name='storageLocation']").val(item.storageLocation);

    openPop();
}
function closePop() {
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

function deleteStroageItem(itemCode) {
    showLoading();
    $.ajax({
        url: "/storage/add/deleteItem",
        data: {"itemCode": itemCode},
        type: "get",
        success: function () {
            setTimeout(function () {
                hideLoading();
                $("tr#" + itemCode).remove();
            }, 1000);
        }
    });
}
function deleteAll() {
    showLoading();
    $.ajax({
        url: "/storage/add/deleteAll",
        success: function (result) {
            setTimeout(function () {
                hideLoading();
            }, 500);
            $('#storagetable tr:gt(0)').remove();
        }
    })
}

function two(two) {
    var three = $.parseJSON(two);
    console.log(three);
    console.log(three.itemCode);
}

function change1() {
    $('#nextPage').load("/test");
    jump2NextPageAndChangeId();
}

function jump2StorageAddItem(){
    $('#nextPage').load("/storage/add/additemajax");
    jump2NextPageAndChangeId();
}






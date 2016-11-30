window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
    document.body.style.display = "block";
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
    var da = $('#applyForm').serialize();
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
            alert("添加成功");
        },
        error: function () {
            alert("添加失败，请重试")
        }
    });
}

<<<<<<< HEAD
function msg_send() {
    showLoading();
    $.ajax({
        url:"/message/sendajax",
        type:"post",
        data: $('#newmessage').serialize(),
        success:function (result) {
            if(result.status == 200){
                //发送成功
                alert("发送成功")
            }else {
                alert("发送失败" )
            }
        },
        error:alert("发送失败" )
    })
    hideLoading();
}

function msg_findmsg() {
    showLoading();
    $.ajax({
        url:"/message/findmessagebyid",
        type:"get",
        success:function (result) {
            //result.content 是一个umessage的list列表
            result.content;
            alert("获取成功")
        },
        error:alert("发送失败" )
    })
    hideLoading();
}

=======
>>>>>>> 7c27124b91eb57c50f957f238b588aee12c3d171
function clearApplyForm() {
    $.ajax({
        url: "/apply/add/clearformajax",
        type: "post",
        data: {},
        success: function () {
            alert("清空成功");
        },
        error: function () {
            alert("清空失败，请重试");
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
function closePop() {
    var popAddBg = document.getElementsByClassName('pop-bg');
    var popAdd = document.getElementsByClassName('pop');
    popAddBg[0].style.background = "rgba(181, 181, 181, 0)";
    popAdd[0].style.transform = "scale(0,0)";
    setTimeout(function () {
        popAddBg[0].style.display = "none";
    }, 500);
}
function openPopAdd(code, name) {

    document.getElementsByName("itemCode")[1].value = code;
    document.getElementsByName("itemName")[1].value = name;
    openPop();
}
function openPopDetails(itemForm) {
    var item = $.parseJSON(itemForm);

    $("[name='itemName']").val(item.itemName);
    $("[name='itemCategory']").val(item.itemCategoryID);
    $("[name='itemCount']").val(item.itemCount);
    $("[name='itemPrice']").val(item.itemPrice);
    $("[name='itemSpec']").val(item.itemSpec);
    $("[name='itemCompany']").val(item.itemCompanyID);
    $("[name='billCode']").val(item.billCode);
    $("[name='storageLocation']").val(item.storageLocation);
    console.log(item.storageLocation);

    openPop();
}

function showLoading() {
    $("#load").css("display", "block");
}

function hideLoading() {
    $("#load").css("display", "none");
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








window.onload = function () {
    document.body.style.fontSize = screen.width * 0.01 + "px";
    document.body.style.display = "block";
};

$(document).ready(function () {
    $("#query").click(function () {
        query();
    });
});

$(document).ready(msg_findmsg());

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
    var da = $('#applyForm').serialize();
    alert(da);
    $.ajax({
        url: "/apply/add/additem",
        type: "post",
        data: da,
        success: function (result) {
            closePop();
            alert("添加成功");
        },
        error: function () {
            alert("添加失败，请重试");
        }
    });
}

function addCategory() {
    var a = $('#form_addCategory').serialize();
    $.ajax({
        url: "/newcategory",
        type: "post",
        data: a,
        success: function (result) {
            closePop();
            alert("添加分类成功");

        },
        error: function () {
            alert("添加分类失败，请重试");
        }
    })
}

function addCompany() {
    var a = $('#form_addCompany').serialize();
    $.ajax({
        url: "/newcompany",
        type: "post",
        data: a,
        success: function (result) {
            closePop();
            alert("添加厂家信息成功");
        },
        error: function () {
            alert("添加厂家信息失败，请重试");
        }
    })
}

function query() {
    var values = $("#queryinput").serialize();
    $.ajax({
        type: "get",
        url: "/query/queryItem",    //向springboot请求数据的url
        data: values,
        success: function (items) {
            $("#result_table").find("tr").remove("tr:gt(0)")
            //没有完成的部分，需要再每次查询后来清空原有的值
            $.each(items, function (i, item) {
                item = "<tr>" +
                    "<td>" + item.itemCode + "</td>" +
                    "<td>" + item.itemName + "</td>" +
                    "<td>" + item.categoryEntity.categoryName + "</td>" +
                    "<td>" + item.itemCount + "</td>" +
                    "<td>" + "操作" + "</td>" +
                    "</tr>";
                $("#result_table").append(item)
            });
        }
    })
}

function msg_send() {
    showLoading();
    $.ajax({
        url: "/message/sendajax",
        type: "post",
        data: $('#newmessage').serialize(),
        success: function (result) {
            if (result.status == 200) {
                //发送成功
                alert("发送成功")
            } else {
                alert("发送失败")
            }
        },
        error: alert("发送失败")
    });
    hideLoading();
}

var msgs = new Array();

function msg_findmsg() {
    var url = "/message/findmessagebyid";
    var type = "get";
    msgFindBy(url, type);
}

function msg_findWarnMsg() {
    var url = "/message/warnmsg";
    var type = "get";
    msgFindBy(url, type);
}

function msgFindBy(url, type) {
    showLoading();
    $.ajax({
        url: url,
        type: type,
        success: function (result) {
            //result.content 是一个umessage的list列表
            var messages = result.content;
            $(".content-right .message").remove();
            var contentRight = $("#message");
            $.each(messages, function (i, message) {
                msgs.push(message);
                messageBox = "<div class='message'>" +
                    "<span class='message-name' >" + message.messageId + "</span>" +
                    "<span class='message-date'>" + message.messageDate + "</span>" +
                    "<div class='message-content' >" + message.messageContent + "</div>" +
                    "<div class='message-operation' onclick=\"openPopMessage('" + i + "')\">详情</div>" +
                    "</div>";
                contentRight.append(messageBox);
            })
        }
    });
    hideLoading();
}

function msg_delete() {
    var messageID = $("[name='messageID']").val();
    $.ajax({
        url: "/message/delete?messageID=" + messageID,
        type: "get",
        success: function (result) {
            if (result.message = "success") {
                //todo
                msg_findmsg();
                alert("success");
            }
        }
    });
    closePop();
}
function findLogs() {//todo
    showLoading();
    $.ajax({
        url: "/log/findLogs",
        type: "get",
        success: function (result) {
            //result.content 是一个umessage的list列表
            var messages = result.content;
            $(".content-right .message").remove();
            var contentRight = $("#message");
            $.each(messages, function (i, message) {
                msgs.push(message);
                messageBox = "<div class='message'>" +
                    "<span class='message-name' >" + message.messageId + "</span>" +
                    "<span class='message-date'>" + message.messageDate + "</span>" +
                    "<div class='message-content' >" + message.messageContent + "</div>" +
                    "<div class='message-operation' onclick=\"openPopMessage('" + i + "')\">详情</div>" +
                    "</div>";
                contentRight.append(messageBox);
            })
        }
    });
    hideLoading();
}

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
//弹出框
function openPop() {
    $(".pop-bg").css('display', 'block');
    setTimeout(function () {
        $(".pop-bg").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop").css('transform', 'scale(1,1)');
    }, 1);
}

function closePop() {
    $(".pop-bg").css('background', 'rgba(181, 181, 181, 0)');
    $(".pop").css('transform', 'scale(0,0)');
    setTimeout(function () {
        $(".pop-bg").css('display', 'none');
    }, 500);
}

function openAddCategory() {
    $(".pop-addCategory").css('display', 'block');
    setTimeout(function () {
        $(".pop-addCategory").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop-addCategory .pop").css('transform', 'scale(1,1)');
    }, 1);
}

function openAddCompany() {
    $(".pop-addCompany").css('display', 'block');
    setTimeout(function () {
        $(".pop-addCompany").css('background', 'rgba(181, 181, 181, 0.5)');
        $(".pop-addCompany .pop").css('transform', 'scale(1,1)');
    }, 1);
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


function openPopMessage(message) {
    // alert(msgs[0]);
    // var d = "";
    // for (var name in msgs[0]) {
    //     d += name + ":" + msgs[0][name] + ";"
    // }
    // console.log(d);
    var msg = msgs[message];

    $("[name='messageID']").val(msg.messageId);
    $("[name='messageType']").val(msg.messageType);
    $("[name='messageContent']").val(msg.messageContent);
    $("[name='messageData']").val(msg.messageDate);
    $("[name='messageSendID']").val(msg.messageSendId);
    $("[name='messageReceiveID']").val(msg.messageReceiveId);
    $("[name='messageState']").val(msg.messageState);
    $("[name='messageTitle']").val(msg.messageTitle);

    openPop();
}


//加载界面
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

